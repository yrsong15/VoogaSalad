/**
 * 
 */
package gameengine.view;

import java.util.ResourceBundle;

import gameengine.view.interfaces.IToolbar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Noel Moon (nm142)
 *
 */
public class Toolbar implements IToolbar {

	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/GameEngineUI";
	
	private ResourceBundle myResources;
	private HBox myToolbar;
	
	public Toolbar() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
		myToolbar = new HBox();
		addButtons();
	}

	@Override
	public HBox getToolbar() {
		return myToolbar;
	}
	
	private void addButtons() {
		myToolbar.getChildren().add(makeButton("ResetButton", event -> reset()));
	}
	
    private void reset() {
    	System.out.println("hey");
	}

	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }
	
}
