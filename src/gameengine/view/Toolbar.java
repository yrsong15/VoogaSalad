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
	private EventHandler<ActionEvent> myLoadGameEvent;
	private EventHandler<ActionEvent> myLoadLevelEvent;
	private EventHandler<ActionEvent> myPauseEvent;
	private EventHandler<ActionEvent> myResetEvent;
	private Button myPauseButton;
	
	public Toolbar(EventHandler<ActionEvent> loadGame, EventHandler<ActionEvent> loadLevel, EventHandler<ActionEvent> pause,
			EventHandler<ActionEvent> reset) {
		myResources = ResourceBundle.getBundle(RESOURCE_FILENAME, Locale.getDefault());
		myLoadGameEvent = loadGame;
		myLoadLevelEvent = loadLevel;
		myPauseEvent = pause;
		myResetEvent = reset;
		myToolbar = new HBox();
		addButtons();
	}

	@Override
	public HBox getToolbar() {
		return myToolbar;
	}
	
	@Override
	public void resume() {
		myPauseButton.setText(myResources.getString("PauseButton"));
	}

	@Override
	public void pause() {
		myPauseButton.setText(myResources.getString("ResumeButton"));
	}
	
	private void addButtons() {
		myPauseButton = makeButton("PauseButton", myPauseEvent);
		myPauseButton.setPrefWidth(70);
		//myToolbar.getChildren().add(myPauseButton);
		myToolbar.getChildren().add(makeButton("ResetButton", myResetEvent));
		//myToolbar.getChildren().addAll(makeButton("LoadGameButton", myLoadGameEvent), makeButton("LoadLevelButton", myLoadLevelEvent),
				//myPauseButton, makeButton("ResetButton", myResetEvent));
	}

	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }
	
}
