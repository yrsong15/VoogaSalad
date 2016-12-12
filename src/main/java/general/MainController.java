package general;
import java.io.File;
import java.io.IOException;
import com.sun.javafx.scene.traversal.Direction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import frontend.util.FileOpener;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import gameengine.model.RandomGenFrame;
import gameengine.model.RandomGenFrameY;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.NoBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import gameengine.network.server.ServerMain;
import gameengine.view.GameCoverSplash;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import objects.*;
import xml.XMLSerializer;
import java.util.ArrayList;
import java.util.HashMap;

public class MainController {
    public static final String STYLESHEET = "default.css";
    private static final String GAME_TITLE = "VoogaSalad";
    private Stage gameEngineStage;
    private Gallery gallery;
    private Level level;
    private GameEditorController gameEditorController;
    private GameEngineController gameEngineController;
    public MainController(Stage stage) throws IOException {
        this.gallery = new Gallery();
        Scene scene = new Scene(new SplashScreen(gallery, this).setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle(GAME_TITLE);
        stage.show();
        initializeGallery();
        gameEngineController = new GameEngineController();
        gameEditorController = new GameEditorController();
    }
    private void initializeGallery() throws IOException {
        this.gallery = new Gallery();
    }

    private void addNewGameFile(String title, String gameData)
    {
        GameFile newGame = new GameFile(title,gameData);
        gallery.addToGallery(newGame);
    }

    public void presentEditor(Game game ) {
        gameEditorController = new GameEditorController();
        gameEditorController.startEditor(game);
        gameEditorController.setOnLoadGame(e -> sendDataToEngine());
    }

    private String testGameEngine(){
        //FOR TESTING PURPOSES ONLY/
        Game game = new Game("Doodle Jump");
        GameObject thirdShyGuy = new GameObject(0, 250, 75, 50, "doodler.png", new HashMap<>());
        Player player1 = new Player(thirdShyGuy);
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        thirdShyGuy.setProperty("movespeed", "10");
        player1.setControl(KeyCode.UP, "up");
        player1.setControl(KeyCode.LEFT, "left");
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.DOWN, "down");


        level = new Level(1);
        level.setTitle(game.getGameName());
        GameBoundary gameBoundaries = new ToroidalBoundary(700, 675, 1200, 800);
        ScrollType scrollType = new ScrollType("FreeScrolling", gameBoundaries);
        scrollType.setScrollSpeed(30);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        level.addPlayer(thirdShyGuy);
        GameObject ground = new GameObject(0, 250,10,800,"pipes.png", new HashMap<>());
        level.addGameObject(ground);
        
        GameObject ground2 = new GameObject(1200, 250,10,800,"pipes.png", new HashMap<>());
        level.addGameObject(ground2);
        
        GameObject ground3 = new GameObject(250, 1000,1200,10,"platform.png", new HashMap<>());
        level.addGameObject(ground3);
        
        GameObject ground4 = new GameObject(250, 0,1200,10,"platform.png", new HashMap<>());
        level.addGameObject(ground4);
        //Left down up right <- order of arrows from left to right
        //UNCOMMENT BELOW FOR DEM SPICY DDR
        /*
        HashMap<String,String> DDRArrowProperties = new HashMap<String,String>();
        RandomGeneration arrow1 = new RandomGeneration(DDRArrowProperties,150,150,"ddrleftarrow.png",2, 20,20,1234,1234,700,800);
        RandomGeneration arrow2 = new RandomGeneration(DDRArrowProperties,150,150,"ddrdownarrow.png",2, 190 ,190,1234,1234,500,520);
        RandomGeneration arrow3 = new RandomGeneration(DDRArrowProperties,150,150,"ddruparrow.png",2, 360,360,1234,1234,300,600);
        RandomGeneration arrow4 = new RandomGeneration(DDRArrowProperties,150,150,"ddrrightarrow.png",2, 530,530,1234,1234,540,1000);
        ArrayList<RandomGeneration> asdf = new ArrayList<RandomGeneration>();
        asdf.add(arrow1);asdf.add(arrow2);asdf.add(arrow3);asdf.add(arrow4);
        RandomGenFrame frame = new RandomGenFrameY(level,asdf,false);
        level.setRandomGenerationFrame(frame);
        */
        //UNCOMMENT BELOW FOR DEM SPICY DOODLE JUMPZ
        /**
        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "1000");
        RandomGeneration platforms = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 3, 0,200,1234,1234,400,500);
        RandomGeneration platforms2 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 3, 200,500,1234,1234,400,500);
        RandomGeneration platforms3 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 3, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> asdf = new ArrayList<RandomGeneration>();
        asdf.add(platforms);
        asdf.add(platforms2);
        asdf.add(platforms3);
        RandomGenFrame frame = new RandomGenFrameY(level,asdf,true);
        level.setRandomGenerationFrame(frame);
        level.addGameObject(ground);**/
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        System.out.println(xml);
        return xml;
    }

    private void setUpGameEngineStage(){
        gameEngineStage = new Stage();
        GameCoverSplash myCover = new GameCoverSplash(level, this);
        gameEngineStage.setScene(myCover.createSplashScene());
        gameEngineStage.setTitle(myCover.getTitle());
        gameEngineStage.show();
    }

    public void startPlaying(){

        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
    }

	private void sendDataToEngine() {
		String title = gameEditorController.getGameTitle();
		String gameFile = gameEditorController.getGameFile();
		addNewGameFile(title, gameFile);
		launchEngine(gameFile);
	}

	public void launchEngine(String XMLData) {
		 XMLData = testGameEngine();
		boolean multiplayer = true;
		boolean isServer = false;
		// if (gameEngineController.startGame(XMLData) == true && (!multiplayer
		// || (multiplayer && !isServer))) {
		if (gameEngineController.startGame(XMLData) == true) {
			setUpGameEngineStage();
		}
	}

    public void editGame() {
        FileOpener chooser = new FileOpener();
        File file = chooser.chooseFile("XML", "data");
        XStream mySerializer = new XStream(new DomDriver());
        Game myGame =  (Game) mySerializer.fromXML(file);
        presentEditor(myGame);
    }
}