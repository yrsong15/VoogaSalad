/**
 * 
 */
package gameengine.view;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author Noel Moon (nm142)
 *
 */
public class MultiplayerPrefScreen {
	public static final double myAppWidth = 270;
	public static final double myAppHeight = 27;
	
	private Scene myScene;
	private ResourceBundle myResources;
	private EventHandler<ActionEvent> myOnlineMultiEvent, myLocalMultiEvent;

	public MultiplayerPrefScreen(ResourceBundle resources, EventHandler<ActionEvent> onlineMultiEvent, EventHandler<ActionEvent> localMultiEvent) {
		myResources = resources;
		myOnlineMultiEvent = onlineMultiEvent;
		myLocalMultiEvent = localMultiEvent;
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	public void reset() {
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	
	public void onlineMulti() {
		BorderPane root = new BorderPane();
		Button hostGame = makeButton("HostButton", null);
		Button joinGame = makeButton("JoinButton", null);
		root.setLeft(hostGame);
		root.setRight(joinGame);
		myScene = new Scene(root, myAppWidth, myAppHeight);
	}
	
	private Pane makeRoot() {
		BorderPane root = new BorderPane();
		Button localButton = makeButton("LocalMultiplayer", myLocalMultiEvent);
		Button onlineButton = makeButton("OnlineMultiplayer", myOnlineMultiEvent);
		localButton.setPrefWidth(135);
		onlineButton.setPrefWidth(135);
		root.setLeft(localButton);
		root.setRight(onlineButton);
		return root;
	}
	
	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        result.setFocusTraversable(false);
        return result;
    }
}
