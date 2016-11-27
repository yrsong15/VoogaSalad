package gameeditor.commanddetails;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import gameeditor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlsDetail extends AbstractCommandDetail {

	private VBox myVBox;
	
	public ControlsDetail() {
		super();
	}
	
	@Override
	public void init() {
		myVBox = new VBox();
		myVBox.setSpacing(myDetailPadding);
		myVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myVBox);
		String [] array = new String [] {"Up", "Down", "Left", "Right", "Shoot", "Interact"};
		for (@SuppressWarnings("unused") String string : array){
			createSelectDirectionsControl("Input", array);
		}
	}
	
	public void createSelectDirectionsControl(String label, String [] optionsArray){
		HBox innerContainer = new HBox();
		innerContainer.setSpacing(hboxSpacing);
		innerContainer.setAlignment(Pos.CENTER);
		ComboBox<String> cb = createComboBox(optionsArray);
		TextArea inputField = createInputField(label, hboxSpacing);
		innerContainer.getChildren().add(cb);
		innerContainer.getChildren().add(inputField);
		myVBox.getChildren().add(innerContainer);
	}
	
	public TextArea createInputField(String label, double hboxSpacing){
		TextArea inputField = new TextArea(label);
		inputField.setMinWidth(paddedDetailWidth);
		inputField.setMaxWidth(paddedDetailWidth);
		inputField.setMinHeight(cbHeight);
		inputField.setMaxHeight(cbHeight);
		inputField.setOnMouseClicked(e -> handleClick(inputField));
		inputField.setOnKeyPressed(e -> inputField.clear());
		inputField.setOnKeyReleased(e -> handleKey(inputField, e.getCode()));
		return inputField;
	}
	
	public ComboBox<String> createComboBox(String [] boxOptions){
		ComboBox<String> cb = new ComboBox<String>();
		cb.getItems().addAll(boxOptions);
		cb.setMinWidth(cbWidth);
		cb.setMaxWidth(cbWidth);
		cb.setMinHeight(cbHeight);
		cb.setMaxHeight(cbHeight);
		return cb;
	}
	
	public void handleClick(TextArea field){
		field.setText("");
	}
	
	public void handleKey(TextArea field, KeyCode kc){
		field.setText(kc.toString());
	}
	public void createTextField(){
		
	}
}
