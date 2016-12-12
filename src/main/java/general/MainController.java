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
    public void launchEngine(String XMLData){
        //XMLData = testGameEngine();
        gameEngineController = new GameEngineController();
        if(gameEngineController.startGame(XMLData) == true){
            setUpGameEngineStage();
        }
    }
    private String testGameEngine(){
        //FOR TESTING PURPOSES ONLY/
        /**Game game = new Game("Doodle Jump");
        GameObject thirdShyGuy = new GameObject(400, 500, 100, 100, "shyguy.png", new HashMap<>());
        Player player3 = new Player(thirdShyGuy);
        game.addPlayer(player3);
        game.addPlayerToClient(0, player3);
        thirdShyGuy.setProperty("jumpunlimited", "800");
        thirdShyGuy.setProperty("gravity", "0.8");
        thirdShyGuy.setProperty("movespeed", "10");
        Level level = new Level(1);
        GameBoundary gameBoundaries = new NoBoundary(700, 675);
        ScrollType scrollType = new ScrollType("LimitedScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        // player1.setControl(KeyCode.A, "jump");
        // player1.setControl(KeyCode.SPACE, "right");
        // player2.setControl(KeyCode.S, "jump");
        player3.setControl(KeyCode.UP, "jump");
        player3.setControl(KeyCode.RIGHT, "right");
        player3.setControl(KeyCode.LEFT, "left");
        // player4.setControl(KeyCode.F, "jump");
        //level.addPlayer(firstShyGuy);
        //  level.addPlayer(secondShyGuy);
        level.addPlayer(thirdShyGuy);
        //  level.addPlayer(fourthShyGuy);
        GameObject ground = new GameObject(0, 570,700,50,"platform.png", new HashMap<>());
        ground.setProperty("nonintersectable", "bottom");**/
        
        
        Game game = new Game("Scrolling Tester");
        GameObject bird = new GameObject(400, 500, 100, 100, "bird3.png", new HashMap<>());
        Player player = new Player(bird);
        game.addPlayer(player);
        game.addPlayerToClient(0, player);
        bird.setProperty("movespeed", "10");
        Level level = new Level(1);
        GameBoundary gameBoundaries = new ToroidalBoundary(700, 675);
        ScrollType scrollType = new ScrollType("FreeScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        player.setControl(KeyCode.UP, "jump");
        player.setControl(KeyCode.RIGHT, "right");
        player.setControl(KeyCode.LEFT, "left");
        level.addPlayer(bird);
        GameObject ground = new GameObject(0, 570,700,50,"platform.png", new HashMap<>());
        ground.setProperty("nonintersectable", "bottom");

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
        RandomGenFrame frame = new RandomGenFrameY(level,asdf);
        level.setRandomGenerationFrame(frame);
        */

        //UNCOMMENT BELOW FOR DEM SPICY DOODLE JUMPZ
        /**
        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "1000");
        RandomGeneration platforms = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 0,200,1234,1234,400,500);
        RandomGeneration platforms2 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 200,500,1234,1234,400,500);
        RandomGeneration platforms3 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> asdf = new ArrayList<>();
        asdf.add(platforms);
        asdf.add(platforms2);
        asdf.add(platforms3);
        RandomGenFrame frame = new RandomGenFrameY(level,asdf);
        level.setRandomGenerationFrame(frame);
        level.addGameObject(ground);**/
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        System.out.println(xml);
        return xml;
    }
    private void setUpGameEngineStage(){
        gameEngineStage = new Stage();
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
    }
    private void sendDataToEngine() {
        String title = gameEditorController.getGameTitle();
        String gameFile = gameEditorController.getGameFile();
        addNewGameFile(title,gameFile);
        launchEngine(gameFile);
    }
    public void editGame(){
        FileOpener chooser= new FileOpener();
        File file = chooser.chooseFile("XML", "data");
        XStream mySerializer = new XStream(new DomDriver());
        Game myGame =  (Game) mySerializer.fromXML(file);
        presentEditor(myGame);
    }
}