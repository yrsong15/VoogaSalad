package general;


import gameeditor.controller.GameEditorController;
import gameeditor.view.GameEditorView;
import gameengine.controller.GameEngineController;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    public static final String STYLESHEET = "default.css";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Replace this with a resource file
    private Stage myGalleryStage;
    private Gallery myGallery;
    private GalleryView myGalleryView;
    private Stage myGameEditorStage;
    //private GameEditorView myGameEditorView;
    private GameEditorController myGameEditorController;
    private GameEngineController myGameEngineController;

    public MainController(Stage stage) {
       
        Scene scene = new Scene(new SplashScreen(stage, this).setUpWindow());
        //GameEditorView myView = new GameEditorView();
        //Scene scene = new Scene(myView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
    }

    public void presentGallery() throws IOException {
        System.out.println("present");
        initializeGallery();
        myGalleryView = new GalleryView(myGallery, this);
        myGalleryStage.setScene(myGalleryView.getScene());
        myGalleryStage.setTitle(GALLERY_STAGE_TITLE);
        myGalleryStage.show();
    }

    private void initializeGallery() throws IOException {
        this.myGallery = new Gallery();
        this.myGalleryStage = new Stage();
// 	   this.gallery = new Gallery();
// 	   for(int i = 0; i < 40; i++)
// 	   {
// 		   myGallery.addToGallery(new GameFile());
// 	   }
// 	   this.galleryStage = new Stage();
    }

    public void presentEditor() {
        myGameEditorStage = new Stage();
        myGameEditorController = new GameEditorController();
        //myGameEditorView = new GameEditorView();
        Scene scene = new Scene(myGameEditorController.startEditor(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myGameEditorStage.setScene(scene);
        myGameEditorStage.show();
    }

    public void launchEngine(String XMLData){
        myGameEngineController = new GameEngineController();
        myGameEngineController.setCurrentXML(XMLData);
        myGameEngineController.startGame();
    }
}