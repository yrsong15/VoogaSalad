package general;


import gameeditor.view.GameEditorView;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainController {
    public static final String STYLESHEET = "default.css";
    public static final String CSS_RESOURCE_PACKAGE = "resources";
    /**
     * IDEALLY, WE GET THE css FOLDER WORKING AND PUT ALL STYLESHEETS THERE.
     * FOR NOW, resources WILL HAVE TO BE TEMPORARY
     */
    public static final String FILE_SEPARATOR = "/";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Replace this with a resource file
    private Stage myGalleryStage;
    private Gallery myGallery;
    private GalleryView myGalleryView;
    private Stage myGameEditorStage;
    private GameEditorView myGameEditorView;

    public MainController(Stage stage) {
        Scene scene = new Scene(new SplashScreen(stage, this).setUpWindow());
        //GameEditorView myView = new GameEditorView();
        //Scene scene = new Scene(myView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
    }

    public void presentGallery() {
        System.out.println("present");
        initializeGallery();
        myGalleryView = new GalleryView(myGallery);
        myGalleryStage.setScene(myGalleryView.getScene());
        myGalleryStage.setTitle(GALLERY_STAGE_TITLE);
        myGalleryStage.show();
    }

    private void initializeGallery() {
        this.myGallery = new Gallery();
        this.myGalleryStage = new Stage();
    }

    public void presentEditor() {
        myGameEditorStage = new Stage();
        myGameEditorView = new GameEditorView();
        Scene scene = new Scene(myGameEditorView.createRoot(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myGameEditorStage.setScene(scene);
        myGameEditorStage.show();
    }

}