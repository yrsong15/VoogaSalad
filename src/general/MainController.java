package general;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public static final String STYLESHEET = "default.css";
    private static final String GAME_TITLE = "VoogaSalad";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery";
    private Stage galleryStage, editorSplashStqage, gameEditorStage, gameEngineStage;
    private Gallery gallery;
    private GalleryView galleryView;
    private GameEditorController gameEditorController;
    private GameEngineController gameEngineController;
    private EditorSplash editorSplash;

    public MainController(Stage stage) throws IOException {
        Scene scene = new Scene(new SplashScreen(this).setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle(GAME_TITLE);
        stage.show();
        initializeGallery();
        gameEngineController = new GameEngineController();
        gameEditorController = new GameEditorController();

    }

    public void presentGallery() {
        galleryView = new GalleryView(gallery, this);
        galleryStage.setScene(galleryView.getScene());
        galleryStage.setTitle(GALLERY_STAGE_TITLE);
        galleryStage.show();
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
        editorSplash = new EditorSplash(this);
        editorSplashStqage = new Stage();
        Scene scene = new Scene(editorSplash.setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        editorSplashStqage.setScene(scene);
        editorSplashStqage.show();
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
}