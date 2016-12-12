package gameeditor.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author John Martin
 * Test class for GameEditor
 * Currently deprecated due to progress in Main Controller. 
 * Under review for reimplementation.
 * 
 */
public class GameEditorTest extends Application {
    
	private GameEditorView myGameEditorView;

    @Override
    public void start (Stage stage) {
    	//myGameEditorView = new GameEditorView();
		Scene scene = new Scene(myGameEditorView.createRoot(), 
				ViewResources.SCENE_WIDTH.getDoubleResource(), ViewResources.SCENE_HEIGHT.getDoubleResource());
	    stage.setScene(scene);
	    stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}