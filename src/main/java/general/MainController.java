package general;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

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


    public void presentEditor2(Game game, String gameType) {

        //                        gameEditorController = new GameEditorController(gameType);
        //                        gameEditorController.startEditor(game);
        //                        gameEditorController.setOnLoadGame(e -> sendDataToEngine());

        testGameEngine();
    }

    private void setUpGameEngineStage(Level level){
        gameEngineStage = new Stage();
        GameCoverSplash myCover = new GameCoverSplash(level, this);
        gameEngineStage.setScene(myCover.createSplashScene());
        gameEngineStage.setTitle(myCover.getTitle());
        gameEngineStage.show();
        //startPlaying();
    }

    public void startPlaying(){
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
    }

    private void sendDataToEngine() {
        String content = null;
//        try {
//            content = new String(Files.readAllBytes(Paths.get("data/vooga10.xml")));
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        //String gameFile = gameEditorController.getGameFile();
        // addNewGameFile(title, gameFile);
        launchEngine(content);
    }

    public void launchEngine(String XMLData) {
        GameExamples gameExamples = new GameExamples();
        XMLData = gameExamples.getDoodleJumpXML();
        boolean multiplayer = true;
        boolean isServer = false;
        Level level = gameEngineController.startGame(XMLData);
        if (level != null) {
            setUpGameEngineStage(level);
        }
    }

    public void testGameEngine(){
        Game game = new Game("Doodle Jump");
        GameObject shyGuy = new GameObject(400, 500, 100, 100, "shyguy.png", new HashMap<>());
        Player player1 = new Player(shyGuy);
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        shyGuy.setProperty("jumpunlimited", "800");
        shyGuy.setProperty("gravity", "1.0");
        shyGuy.setProperty("movespeed", "20");
        shyGuy.setProperty("health", "30");
        Level level = new Level(1);
        GameBoundary gameBoundaries = new ToroidalBoundary(700, 675);
        ScrollType scrollType = new ScrollType("LimitedScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");

        game.setCurrentLevel(level);

        player1.setControl(KeyCode.UP, "jump");
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.LEFT, "left");
        player1.setControl(KeyCode.SPACE, "shoot");

        level.addPlayer(shyGuy);

        GameObject ground = new GameObject(0, 570,700,50,"platform.png", new HashMap<>());
        ground.setProperty("nonintersectable", "bottom");
        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "1000");
        RandomGeneration platforms = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 0,200,1234,1234,400,500);
        RandomGeneration platforms2 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 200,500,1234,1234,400,500);
        RandomGeneration platforms3 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> randomGen = new ArrayList<>();
        randomGen.add(platforms);
        randomGen.add(platforms2);
        randomGen.add(platforms3);
        RandomGenFrame frame = new RandomGenFrameY(level, randomGen, true);
        level.setRandomGenerationFrame(frame);
        level.addGameObject(ground);
        ProjectileProperties projectileProperties = new ProjectileProperties("doodler.png", 30, 30, Direction.UP, 400, 500, 30, 3);
        shyGuy.setProjectileProperties(projectileProperties);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        launchEngine(xml);

        try {
            FileUtils.writeStringToFile(new File("test.txt"), xml);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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