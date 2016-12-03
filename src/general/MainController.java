package general;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import gameengine.view.GameEngineUI;
import gameengine.view.GameScreen;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import objects.Game;
import objects.GameObject;
import objects.Level;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import com.sun.javafx.scene.traversal.Direction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import objects.RandomGeneration;
import objects.ScrollType;
public class MainController {

    public static final String STYLESHEET = "default.css";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Replace this with a resource file
    private Stage myGalleryStage, myEditorSplashStage, myGameEditorStage, myGameEngineStage;
    private Stage mainStage;
    private Gallery myGallery;
    private GalleryView myGalleryView;
    private GameEditorController myGameEditorController;
    private GameEngineController myGameEngineController;
    private EditorSplash myEditorSplash;

    public MainController(Stage stage) throws IOException {
        mainStage = stage;
        Scene scene = new Scene(new SplashScreen(stage, this).setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
        initializeGallery();
    }
    public void presentGallery() {
        //System.out.println("present");
        myGalleryView = new GalleryView(myGallery, this);
        myGalleryStage.setScene(myGalleryView.getScene());
        myGalleryStage.setTitle(GALLERY_STAGE_TITLE);
        myGalleryStage.show();
    }
    private void initializeGallery() throws IOException {
        this.myGallery = new Gallery();
        this.myGalleryStage = new Stage();
    }
    private void addNewGameFile(String title, String gameData)
    {
        GameFile newGame = new GameFile(title,gameData);
        myGallery.addToGallery(newGame);
    }

    public void editorSplash(){
        myEditorSplash = new EditorSplash(this);
        myEditorSplashStage = new Stage();
        Scene scene = new Scene(myEditorSplash.setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        myEditorSplashStage.setScene(scene);
        myEditorSplashStage.show();
    }

    public void presentEditor() {
        myGameEditorStage = new Stage();
        myGameEditorController = new GameEditorController();
       // Scene scene = new Scene(myGameEditorController.startEditor(), SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_HEIGHT);
        //myGameEditorStage.setScene(scene);
        //scene.getStylesheets().add("gameEditorSplash.css");
       // myGameEditorStage.show();
        //myGameEditorController.setOnLoadGame(e -> sendDataToEngine());
        myGameEditorController.startEditor();
    }
    
    
    
    //TODO: Remove hardcoded values in this method and the ones after it! Let's make another properties file or something for these strings
    public void launchEngine(String XMLData){
        setMyGameEngineController(XMLData);
        myGameEngineController.setCurrentXML(XMLData);
        if(myGameEngineController.startGame()) {
            setMyGameEngineStage();
        }
    }

    private void setMyGameEngineController(String xmlData){
        myGameEngineController = new GameEngineController();
        myGameEngineController.setCurrentXML(xmlData);
    }
    private void setMyGameEngineStage(){
        myGameEngineStage = new Stage();
        myGameEngineStage.setOnCloseRequest(event -> myGameEngineStage.close());
        myGameEngineStage.setOnCloseRequest(event -> myGameEngineController.stop());
        myGameEngineStage.setScene(myGameEngineController.getScene());
        myGameEngineStage.show();
    }


    private void sendDataToEngine() {
        String title = myGameEditorController.getGameTitle();
        String gameFile = myGameEditorController.getGameFile();
        addNewGameFile(title,gameFile);
        launchEngine(gameFile);
    }
}