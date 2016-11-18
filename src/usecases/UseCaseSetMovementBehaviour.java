package usecases;

import gameeditor.interfaces.IGameEditorFrontend;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import usecases.mockObjects.UseCaseGObject;

/**
 * 
 * This is a use case that corresponds to the following situation:
 * 
 * An sprite object is selected in the Game Editor, the movement behaviors are
 * then set accordingly, adding the properties to the corresponding GObject element
 * in the XML file to be passed to the Game Engine.
 * 
 * This use case is defined from the front-end perspective and includes the setting
 * of a control method (for example if this was a controllable main character).
 * 
 * @author John Martin (jfm41)
 *
 */

public class UseCaseSetMovementBehaviour {
	private final static String MOVEMENT_TYPE_STUB = "Movement Type";
	private final static String MOVEMENT_CONTROL_STUB = "Control";
	
	private UseCaseGObject mySelectedObject;
	private ComboBox<String> myBehaviourOptions; 
	private ComboBox<String> myDirectionOptions;
	private TextArea myControlInput;
	private Button mySetButton;
	private IGameEditorFrontend myXMLEditor;

	public UseCaseSetMovementBehaviour(IGameEditorFrontend frontendEditor) {
		// TODO Auto-generated constructor stub
		myXMLEditor = frontendEditor;
		myBehaviourOptions = new ComboBox<String>();
		myBehaviourOptions.getItems().addAll("Smooth", "Stepped");
		myDirectionOptions = new ComboBox<String>();
		myDirectionOptions.getItems().addAll("Smooth", "Stepped");
		myControlInput = new TextArea();
		mySetButton = new Button();
		mySetButton.setOnAction(e -> this.setBehaviour(mySelectedObject, myControlInput.getText(), myBehaviourOptions.getValue(), myDirectionOptions.getValue()));
	}
	
	public void setBehaviour(UseCaseGObject object, String movementDirection, String controlKey, String movementType) {
		String objectName = object.toString();
		myXMLEditor.setSubProperty(objectName, movementDirection, MOVEMENT_CONTROL_STUB, controlKey);
		myXMLEditor.setSubProperty(objectName, movementDirection, MOVEMENT_TYPE_STUB, controlKey);
	}

}
