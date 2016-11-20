/**
 * 
 */
package gameengine.view;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import gameengine.view.interfaces.IToolbar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Noel Moon (nm142)
 *
 */
public class Toolbar implements IToolbar {

	public static final String RESOURCE_FILENAME = "GameEngineUI";
	
	private ResourceBundle myResources;
	private HBox myToolbar;
	private String myGameFileLocation;
	
	public Toolbar() {
		myResources = ResourceBundle.getBundle(RESOURCE_FILENAME, Locale.getDefault());
		myToolbar = new HBox();
		addButtons();
	}

	@Override
	public HBox getToolbar() {
		return myToolbar;
	}
	
	private void addButtons() {
		myToolbar.getChildren().addAll(makeButton("LoadGameButton", event -> loadGame()), makeButton("StartButton", event -> start()), 
				makeButton("StopButton", event -> stop()), makeButton("ResetButton", event -> reset()));
	}
	
	private void loadGame() {
		FileChooser gameChooser = new FileChooser();
		gameChooser.setTitle("Open Resource File");
		//gameChooser.setInitialDirectory(getInitialDirectory());
		File gameFile = gameChooser.showOpenDialog(new Stage());
		myGameFileLocation = gameFile.getAbsolutePath();
		System.out.println(myGameFileLocation);
	}
	
    private void reset() {
    	System.out.println("reset");
	}
    
    private void stop() {
    	System.out.println("stop");
    }
    
    private void start() {
    	System.out.println("start");
    }

	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }
	
}
