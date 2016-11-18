package general;


import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainController {
   public static final String STYLESHEET = "default.css";

   public static final String CSS_RESOURCE_PACKAGE = "resources";
   public static final String FILE_SEPARATOR = "/";
   private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Replace this with a resource file
   private Stage galleryStage;
   private Gallery gallery;

   public MainController(Stage stage){
        Scene scene = new Scene(new SplashScreen(stage,this).setUpWindow());
        //GameEditorView myView = new GameEditorView();
        //Scene scene = new Scene(myView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
        scene.getStylesheets().add(CSS_RESOURCE_PACKAGE + FILE_SEPARATOR + STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
       initializeGallery();
    }

    public void presentGallery() {
        System.out.println("present");
        GalleryView galleryView = new GalleryView(gallery);
        galleryStage.setScene(galleryView.getScene());
        galleryStage.setTitle(GALLERY_STAGE_TITLE);
        galleryStage.show();
    }
    
    private void initializeGallery() {
 	   this.gallery = new Gallery();
 	   for(int i = 0; i < 40; i++)
 	   {
 		   gallery.addToGallery(new GameFile());
 	   }
 	   this.galleryStage = new Stage();
    }

 

}