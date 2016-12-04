package general;
import java.io.File;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import frontend.util.FileOpener;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import javafx.scene.Scene;
import javafx.stage.Stage;

import objects.Game;

import java.io.IOException;

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


  //TODO: Remove hardcoded values in this method and the ones after it! Let's make another properties file or something for these strings
    public void presentEditor(Game game ) {
        gameEditorStage = new Stage();
        gameEditorController = new GameEditorController();
        // Scene scene = new Scene(myGameEditorController.startEditor(), SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_HEIGHT);
        //myGameEditorStage.setScene(scene);
        //scene.getStylesheets().add("gameEditorSplash.css");
        // myGameEditorStage.show();
        gameEditorController.startEditor(game);
        gameEditorController.setOnLoadGame(e -> sendDataToEngine());   
    }


    public void launchEngine(String XMLData){
        if(gameEngineController.startGame(XMLData) == true){
            setUpGameEngineStage();
        };
    }

    private void setUpGameEngineStage(){
        gameEngineStage = new Stage();
        gameEngineStage.setOnCloseRequest(event -> gameEngineStage.close());
        gameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
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