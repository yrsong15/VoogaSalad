package gameeditor.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test class for GameEditor
 */
public class GameEditorTest extends Application {
    
	private GameEditorView myGameEditorView;

    @Override
    public void start (Stage stage) {
    	myGameEditorView = new GameEditorView();
		Scene scene = new Scene(myGameEditorView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
	    stage.setScene(scene);
	    stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}