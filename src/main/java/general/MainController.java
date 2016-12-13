package general;
import java.io.File;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import frontend.util.FileOpener;
import gameeditor.controller.GameEditorController;
import gameeditor.xml.XMLSerializer;
import gameengine.controller.GameEngineController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
//    private static final String GALLERY_STAGE_TITLE = "Game Gallery";
//    private Stage galleryStage, editorSplashStage, gameEditorStage, gameEngineStage;
    private Stage gameEngineStage;
    private Gallery gallery;
//    private GalleryView galleryView;
    private GameEditorController gameEditorController;
    private GameEngineController gameEngineController;
//    private EditorSplash editorSplash;

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

    private void addNewGameFile(String title, String gameData, Image gameCoverImage)
    {
        GameFile newGame = new GameFile(title,gameData,gameCoverImage);
        gallery.addToGallery(newGame);
    }


    public void presentEditor(Game game ) {
        gameEditorController = new GameEditorController();
        gameEditorController.startEditor(game);
        gameEditorController.setOnLoadGame(e -> sendDataToEngine());   
    }


    public void launchEngine(String XMLData){
       // XMLData = testGameEngine();
        if(gameEngineController.startGame(XMLData) == true){
            setUpGameEngineStage();
        };
    }

//    private String testGameEngine(){
//        //FOR TESTING PURPOSES ONLY
//        Game game = new Game("Test Game");
//        Level level = new Level(1);
//        ScrollType scrollType = new ScrollType("ForcedScrolling");
//        scrollType.addScrollDirection(Direction.RIGHT);
//        scrollType.setScrollSpeed(30);
//        level.setScrollType(scrollType);
//        level.setBackgroundImage("bg.png");
//        game.setCurrentLevel(level);
//        GameObject mainChar = new GameObject(100, 100, 100, 100, "bird3.png", new HashMap<>());
//        level.setMainCharacter(mainChar);
//        XMLSerializer testSerializer = new XMLSerializer();
//        String xml = testSerializer.serializeGame(game);
//        return xml;
//    }

    private void setUpGameEngineStage(){
        gameEngineStage = new Stage();
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.reset());
    }

    private void sendDataToEngine() {
        String title = gameEditorController.getGameTitle();
        String gameFile = gameEditorController.getGameFile();
        Image gameCoverImage = gameEditorController.getGameCoverImage();
        addNewGameFile(title,gameFile,gameCoverImage);
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