package general;
import java.io.File;
import java.io.IOException;

import com.sun.javafx.scene.traversal.Direction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import frontend.util.FileOpener;
import gameeditor.controller.GameEditorController;
import gameeditor.xml.XMLSerializer;
import gameengine.controller.GameEngineController;
import gameengine.model.boundary.ScreenBoundary;
import gameengine.model.boundary.StopAtEdgeBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import objects.*;

import java.io.IOException;
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
        //FOR TESTING PURPOSES ONLY
        Game game = new Game("Test Game");
        GameObject mainChar = new GameObject(100, 100, 100, 100, "bird3.png", new HashMap<>());
        Player player = new Player(mainChar);
        game.addPlayer(player);
        ProjectileProperties properties = new ProjectileProperties("duvall.png", 30, 30, Direction.RIGHT, 500, 30, 20);
        player.getMainChar().setProjectileProperties(properties);
        mainChar.setProperty("horizontalmovement", "10");
        mainChar.setProperty("gravity", "0.8");
        mainChar.setProperty("jump", "400");
        mainChar.setProperty("health", "10");
        mainChar.setProperty("movespeed", "30");
        Level level = new Level(1);
        ScreenBoundary gameBoundaries = new ToroidalBoundary(700, 675);
        ScrollType scrollType = new ScrollType("ForcedScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.RIGHT);
        scrollType.setScrollSpeed(30);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        player.setControl(KeyCode.W, "jump");
        player.setControl(KeyCode.D, "right");
        player.setControl(KeyCode.SPACE, "shoot");
        level.addPlayer(mainChar);
        GameObject ground = new GameObject(0,600,1000000,200, new HashMap<>());
        ground.setProperty("damage","0");
        ground.setProperty("nonintersectable", "true");
        level.addGameObject(ground);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);

    	//doodle jump configuration
    	/**
    	 Game game = new Game("Doodle Jump");
         GameObject mainChar = new GameObject(250, 250, 75, 50, "doodler.png", new HashMap<>());
         Player player = new Player(mainChar);
         game.addPlayer(player);
         mainChar.setProperty("gravity", "0.8");
         mainChar.setProperty("jump", "400");
         mainChar.setProperty("health", "10");
         mainChar.setProperty("movespeed", "30");
         Level level = new Level(1);
         ScreenBoundary gameBoundaries = new StopAtEdgeBoundary(700, 675);
         ScrollType scrollType = new ScrollType("FreeScrolling", gameBoundaries);
         scrollType.addScrollDirection(Direction.RIGHT);
         scrollType.setScrollSpeed(30);
         level.setScrollType(scrollType);
         level.setBackgroundImage("Background/graphPaper.png");
         game.setCurrentLevel(level);
         player.setControl(KeyCode.W, "jump");
         player.setControl(KeyCode.LEFT, "left");
         player.setControl(KeyCode.RIGHT, "right");
         player.setControl(KeyCode.UP, "up");
         player.setControl(KeyCode.DOWN, "down");
         player.setControl(KeyCode.SPACE, "shoot");
         level.addPlayer(mainChar);
         GameObject ground = new GameObject(250,200,75,50, "platform.png", new HashMap<>());
         ground.setProperty("nonintersectable", "true");
         level.addGameObject(ground);
         XMLSerializer testSerializer = new XMLSerializer();
         String xml = testSerializer.serializeGame(game);***/
         level.addGameObject(ground);
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