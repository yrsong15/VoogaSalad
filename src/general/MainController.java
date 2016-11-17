package general;


import java.io.File;
import gameEditorView.GameEditorView;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainController {
   public static final String STYLESHEET = "default.css";
     public static final String CSS_RESOURCE_PACKAGE = "resources";
    public static final String FILE_SEPARATOR = "/";

    public MainController(Stage stage){
        Scene scene = new Scene(new SplashScreen(stage).setUpWindow());
        //GameEditorView myView = new GameEditorView();
        //Scene scene = new Scene(myView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
        scene.getStylesheets().add(CSS_RESOURCE_PACKAGE + FILE_SEPARATOR + STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
    }

}