/**
 * 
 */
package gameengine.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
 * @author Noel Moon (nm142), Ray Song
 *
 */
public class Toolbar implements IToolbar {
	
	private final int DEFAULT_BUTTON_WIDTH = 75;
	private ResourceBundle myResources;
	private HBox myToolbar;
	
	private EventHandler<ActionEvent> myPauseEvent;
	private EventHandler<ActionEvent> myResetEvent;
	private EventHandler<ActionEvent> myMuteEvent;
	private EventHandler<ActionEvent> mySaveEvent;
	private Button myPauseButton;
	private Button myMuteButton;
	private Button myResetButton;
	private Button mySaveButton;
	
	public Toolbar(ResourceBundle resources, EventHandler<ActionEvent> loadLevel, EventHandler<ActionEvent> pause, 
			EventHandler<ActionEvent> reset, EventHandler<ActionEvent> mute, EventHandler<ActionEvent> save) {
		myResources = resources;
		myResetEvent = reset;
		myMuteEvent = mute;
		mySaveEvent = save;
		myToolbar = new HBox();
		myToolbar.setPrefHeight(40);
		addButtons();
	}

	public HBox getToolbar() {
		return myToolbar;
	}
	
	public void resume() {
		myPauseButton.setText(myResources.getString("PauseButton"));
	}

	public void pause() {
		myPauseButton.setText(myResources.getString("ResumeButton"));
	}
	
	public void mute() {
		myMuteButton.setText(myResources.getString("UnmuteButton"));
	}
	
	public void unmute() {
		myMuteButton.setText(myResources.getString("MuteButton"));
	}
	
	
	private void addButtons() {
		ArrayList<Button> listOfButtons = new ArrayList<Button>();
		myResetButton = makeButton("ResetButton", myResetEvent, listOfButtons);
		myMuteButton = makeButton("MuteButton", myMuteEvent, listOfButtons);
		myPauseButton = makeButton("PauseButton", myPauseEvent, listOfButtons);
		mySaveButton = makeButton("SaveButton", mySaveEvent, listOfButtons);
		
		addButtonToToolbar(listOfButtons);
	}
	
	private void addButtonToToolbar(List<Button> listOfButtons){
		for(Button button: listOfButtons){
			button.setPrefWidth(DEFAULT_BUTTON_WIDTH);
			myToolbar.getChildren().add(button);	
		}
	}

	private Button makeButton(String property, EventHandler<ActionEvent> handler, List<Button> list) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        result.setFocusTraversable(false);
        list.add(result);
        return result;
    }
	
}