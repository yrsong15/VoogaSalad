package general;
import com.sun.javafx.scene.traversal.Direction;
import gameeditor.controller.GameEditorController;
import gameeditor.xml.XMLSerializer;
import gameengine.controller.GameEngineController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Game;
import objects.GameObject;
import objects.Level;
import objects.ScrollType;

import java.io.IOException;
import java.util.HashMap;

public class MainController {

    public static final String STYLESHEET = "default.css";
    private static final String GAME_TITLE = "VoogaSalad";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery";
    private Stage galleryStage, editorSplashStage, gameEditorStage, gameEngineStage;
    private Gallery gallery;
    private GalleryView galleryView;
    private GameEditorController gameEditorController;
    private GameEngineController gameEngineController;
    private EditorSplash editorSplash;

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

    public void presentGallery() {
        //System.out.println("present");
//        myGalleryView = new GalleryView(myGallery, this);
//        myGalleryStage.setScene(myGalleryView.getScene());
//        myGalleryStage.setTitle(GALLERY_STAGE_TITLE);
//        myGalleryStage.show();
    }

    private void initializeGallery() throws IOException {
        this.gallery = new Gallery();
        this.galleryStage = new Stage();
    }

    private void addNewGameFile(String title, String gameData)
    {
        GameFile newGame = new GameFile(title,gameData);
        gallery.addToGallery(newGame);
    }

    public void editorSplash(){
//        myEditorSplash = new EditorSplash(this);
//        myEditorSplashStage = new Stage();
//        Scene scene = new Scene(myEditorSplash.setUpWindow());
//        scene.getStylesheets().add(STYLESHEET);
//        myEditorSplashStage.setScene(scene);
//        myEditorSplashStage.show();
    }

    public void presentEditor() {
        gameEditorStage = new Stage();
        Scene scene = new Scene(gameEditorController.startEditor(), SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_HEIGHT);
        gameEditorStage.setScene(scene);
        scene.getStylesheets().add("gameEditorSplash.css");
        gameEditorStage.show();
        gameEditorController.setOnLoadGame(e -> sendDataToEngine());
    }


    public void launchEngine(String XMLData){
       // XMLData = testGameEngine();
        if(gameEngineController.startGame(XMLData) == true){
            setUpGameEngineStage();
        };
    }

    private String testGameEngine(){
        //FOR TESTING PURPOSES ONLY
        Game game = new Game("Test Game");
        Level level = new Level(1);
        ScrollType scrollType = new ScrollType("ForcedScrolling");
        scrollType.addScrollDirection(Direction.RIGHT);
        scrollType.setScrollSpeed(30);
        level.setScrollType(scrollType);
        level.setBackgroundImage("bg.png");
        game.setCurrentLevel(level);
        GameObject mainChar = new GameObject(100, 100, 100, 100, "bird3.png", new HashMap<>());
        level.setMainCharacter(mainChar);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }

    private void setUpGameEngineStage(){
        gameEngineStage = new Stage();
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.reset());
    }

    private void sendDataToEngine() {
        String title = gameEditorController.getGameTitle();
        String gameFile = gameEditorController.getGameFile();
        addNewGameFile(title,gameFile);
        launchEngine(gameFile);
    }
}