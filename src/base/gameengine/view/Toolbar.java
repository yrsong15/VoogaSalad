/**
 * 
 */
package base.gameengine.view;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Noel Moon (nm142)
 *
 */
public class Toolbar implements IToolbar {

	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.properties/Button.properties";
	
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
		myToolbar.getChildren().add(makeButton("ResetButton", null));
	}
	
    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }
	
}
