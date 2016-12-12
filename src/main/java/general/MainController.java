package general;

import java.io.File;
import java.io.IOException;
import com.sun.javafx.scene.traversal.Direction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import frontend.util.FileOpener;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import gameengine.model.boundary.NoBoundary;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import gameengine.network.server.ServerMain;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import objects.*;
import xml.XMLSerializer;
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


	public void presentEditor(Game game) {
		gameEditorController = new GameEditorController();
		gameEditorController.startEditor(game);
		gameEditorController.setOnLoadGame(e -> sendDataToEngine());
	}

    private String testGameEngine(){
        //FOR TESTING PURPOSES ONLY/
        Game game = new Game("Dance Dance Revolution");
        GameObject firstShyGuy = new GameObject(0, 100, 500, 100, 100, "shyguy.png", new HashMap<>());
        GameObject secondShyGuy = new GameObject(0, 250, 500, 100, 100, "shyguy.png", new HashMap<>());
        GameObject thirdShyGuy = new GameObject(0, 400, 500, 100, 100, "shyguy.png", new HashMap<>());
        GameObject fourthShyGuy = new GameObject(0, 550, 500, 100, 100, "shyguy.png", new HashMap<>());
        Player player1 = new Player(firstShyGuy);
        Player player2 = new Player(secondShyGuy);
        Player player3 = new Player(thirdShyGuy);
        Player player4 = new Player(fourthShyGuy);
        game.addPlayerToClient(0, player1);
        game.addPlayerToClient(1, player2);
//        game.addPlayer(player3);
//        game.addPlayer(player4);
        firstShyGuy.setProperty("jumponce", "400");
        secondShyGuy.setProperty("jumponce", "400");
        thirdShyGuy.setProperty("jumponce", "400");
        fourthShyGuy.setProperty("jumponce", "400");
        firstShyGuy.setProperty("gravity", "0.8");
        secondShyGuy.setProperty("gravity", "0.8");
        thirdShyGuy.setProperty("gravity", "0.8");
        fourthShyGuy.setProperty("gravity", "0.8");
        ProjectileProperties projectileProperties = new ProjectileProperties("duvall.png", 50, 50, Direction.RIGHT, 400, 30, 20, 2);
        firstShyGuy.setProjectileProperties(projectileProperties);
        Level level = new Level(1);
        GameBoundary gameBoundaries = new NoBoundary(700, 675);
        ScrollType scrollType = new ScrollType("LimitedScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.RIGHT);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        player1.setControl(KeyCode.UP, "jump");
        player1.setControl(KeyCode.SPACE, "shoot");
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.LEFT, "left");
        player2.setControl(KeyCode.S, "jump");
        player3.setControl(KeyCode.D, "jump");
        player4.setControl(KeyCode.F, "jump");
        level.addPlayer(firstShyGuy);
        level.addPlayer(secondShyGuy);
        level.addPlayer(thirdShyGuy);
        level.addPlayer(fourthShyGuy);
        GameObject ground = new GameObject(0, 570,700,50,"ground.png", new HashMap<>());
        ground.setProperty("nonintersectable", "true");
        level.addGameObject(ground);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
/**
    	//doodle jump configuration
    	
    	 Game game = new Game("Doodle Jump");
         GameObject mainChar = new GameObject(250, 250, 75, 50, "doodler.png", new HashMap<>());
         Player player = new Player(mainChar);
         game.addPlayer(player);
         mainChar.setProperty("gravity", "0");
         mainChar.setProperty("jump", "400");
         mainChar.setProperty("health", "10");
         mainChar.setProperty("movespeed", "10");
         Level level = new Level(1);
         GameBoundary gameBoundaries = new ToroidalBoundary(700, 675, 1200, 1000);
         ScrollType scrollType = new ScrollType("FreeScrolling", gameBoundaries);
         scrollType.addScrollDirection(Direction.UP);
         scrollType.setScrollSpeed(30);
         level.setScrollType(scrollType);
         level.setBackgroundImage("Background/bg.png");
         game.setCurrentLevel(level);
         player.setControl(KeyCode.W, "jump");
         player.setControl(KeyCode.LEFT, "left");
         player.setControl(KeyCode.RIGHT, "right");
         player.setControl(KeyCode.UP, "up");
         player.setControl(KeyCode.DOWN, "down");
         player.setControl(KeyCode.SPACE, "shoot");
         level.addPlayer(mainChar);
         
         GameObject left = new GameObject(0,250,10,800, "pipes.png", new HashMap<>());
         level.addGameObject(left);
         
         GameObject right = new GameObject(1200,250,10,800, "pipes.png", new HashMap<>());
         level.addGameObject(right);
         
         GameObject top = new GameObject(250,1000,1200,10, "platform.png", new HashMap<>());
         level.addGameObject(top);
         
         GameObject bottom = new GameObject(250,0,1200,10, "platform.png", new HashMap<>());
         level.addGameObject(bottom);
         /**
         GameObject ground = new GameObject(250,250,100,50, "platform.png",new HashMap<>());
         ground.setProperty("damage","0");
         ground.setProperty("nonintersectable", "true");
         level.addGameObject(ground);**/
        return xml;
    }
    private void setUpGameEngineStage(){
        gameEngineStage = new Stage();
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
        gameEngineStage.setResizable(false);
    }



	private void sendDataToEngine() {
		String title = gameEditorController.getGameTitle();
		String gameFile = gameEditorController.getGameFile();
		addNewGameFile(title, gameFile);
		launchEngine(gameFile);
	}


	public void launchEngine(String XMLData) {
		XMLData = testGameEngine();
		boolean multiplayer = false;
		boolean isServer = false;
		if (gameEngineController.startGame(XMLData) == true) {
			setUpGameEngineStage();
		}
	}

	public void editGame() {
		FileOpener chooser = new FileOpener();
		File file = chooser.chooseFile("XML", "data");
		XStream mySerializer = new XStream(new DomDriver());
		Game myGame = (Game) mySerializer.fromXML(file);
		presentEditor(myGame);
	}
}