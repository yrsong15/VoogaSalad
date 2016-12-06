package gameeditor.commanddetails;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import gameeditor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlsDetail extends AbstractCommandDetail {

	private VBox myVBox;
	private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
	private ArrayList<String> myControlsOptions;
	private ArrayList<TextArea> myInputFields = new ArrayList<TextArea>();
	
	public ControlsDetail() {
		super();
	}
	
	@Override
	public void init() {
		myVBox = new VBox();
		myVBox.setSpacing(myDetailPadding);
		myVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myVBox);
		getControlsArray();
		for (@SuppressWarnings("unused") String string : myControlsOptions){
			createSelectDirectionsControl("Input", myControlsOptions);
		}
		createSave();
	}
	
	public void getControlsArray(){
		String [] array = new String [] {"Up", "Down", "Right", "Left", "Jump", "Shoot"};
		myControlsOptions = new ArrayList<String>();
		for (String a : array){
			myControlsOptions.add(a);
		}
	}
	
	public void createSave(){
		Button save = new Button();
		save.setText("Save Controls");
		save.setMinWidth(cbWidth);
		save.setMinHeight(cbHeight);
		save.setOnMouseClicked(e-> {handleSave();});
		myVBox.getChildren().add(save);
	}
	
	public void handleSave(){
		ResourceBundle controlProps =  ResourceBundle.getBundle("Controls");
		for (int i = 0; i < myComboBoxes.size(); i++){
			String controlKey = myComboBoxes.get(i).getValue();
			String kcString = myInputFields.get(i).getText();
			//System.out.println("KCString: " + kcString);
			if (controlKey != null && kcString != null && kcString !="Input"){
				KeyCode kc = KeyCode.valueOf(kcString);
				myDataStore.addControl(kc, controlProps.getString(controlKey.toLowerCase()));
			}
		}
		
//		ResourceBundle geprops =  ResourceBundle.getBundle("GameEditorProperties");
//		Enumeration<String> enumKeys = geprops.getKeys();
//		Map<String, String> propertiesMap = new HashMap<String, String>();
//		for (ComboBox<String> cb : myComboBoxes){
//			propertiesMap.put(enumKeys.nextElement(), cb.getValue());
//		}
//		propertiesMap.put(DetailResources.TYPE_NAME.getResource(), myTypeTextArea.getText());
//		propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), myFilePath);
//		myDataStore.storeType(propertiesMap);
	}
	
	public void createSelectDirectionsControl(String label, ArrayList<String> optionsArray){
		HBox innerContainer = new HBox();
		innerContainer.setSpacing(hboxSpacing);
		innerContainer.setAlignment(Pos.CENTER);
		ComboBox<String> cb = createComboBox(optionsArray);
		TextArea inputField = createInputField(label, hboxSpacing);
		myInputFields.add(inputField);
		myComboBoxes.add(cb);
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
	
	public ComboBox<String> createComboBox(ArrayList<String> boxOptions){
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
