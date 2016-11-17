package general;


import java.io.File;
import gameEditorView.GameEditorView;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainController {
   public static final String STYLESHEET = "default.css";
   public static final String CSS_RESOURCE_PACKAGE = "resources";
   
   private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Extract this into a resource file
   private Gallery gallery;
   private Stage galleryStage;
   public MainController(Stage stage){
        Scene scene = new Scene(new SplashScreen(stage,this).setUpWindow());
        //GameEditorView myView = new GameEditorView();
        //Scene scene = new Scene(myView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
        scene.getStylesheets().add(CSS_RESOURCE_PACKAGE + "/" + STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show(); 	
        initializeGallery();
    }
   
    public void presentGallery() {
    	GalleryView galleryView = new GalleryView(gallery);
    	galleryStage.setScene(galleryView.getScene());
    	galleryStage.setTitle(GALLERY_STAGE_TITLE); //TODO: Extract this into a resource file
    	galleryStage.show();
    	System.out.println("Inside of present Gallery");
    }
    
    private void initializeGallery() {
 	   this.gallery = new Gallery();
 	   this.galleryStage = new Stage();
    }

}