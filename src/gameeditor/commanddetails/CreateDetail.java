package gameeditor.commanddetails;

import java.io.File;
import java.util.Locale;

import frontend.util.FileOpener;
import gameeditor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CreateDetail extends AbstractCommandDetail  {
	
	private double cbWidth = 7*ViewResources.AVATAR_ZONE_WIDTH.getDoubleResource()/15 - myDetailPadding;
	private double cbHeight = 30;
	private String myFilePath;
	private VBox myPropertiesVBox;
	
	public CreateDetail() {
		super();
		myPropertiesVBox = new VBox();
		myPropertiesVBox.setSpacing(myDetailPadding);
		myPropertiesVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myPropertiesVBox);	
		createTypeName();
		createProperties();
		createImageChoose();
	}
	
	public void createImageChoose(){
		Button choose = new Button();
		choose.setText("Select Sprite Image");
		choose.setMinWidth(cbWidth);
		choose.setMinHeight(cbHeight);
		choose.setOnAction((e) -> { setSpriteImage(); });
		myPropertiesVBox.getChildren().add(choose);
	}
	
	public void setSpriteImage(){
	    myFilePath = getFilePath(ViewResources.IMAGE_FILE_TYPE.getResource(), ViewResources.SPRITE_IMAGE_LOCATION.getResource());       
	}
	
	private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file =(myFileOpener.chooseFile(fileType, fileLocation));
        if(file !=null){
            return file.toURI().toString();
        }
        return null;
    }
	
	public void createTypeName(){
		TextArea inputField = new TextArea(DetailResources.TYPE_NAME.getResource());
		inputField.setMinWidth(myPaneWidth-4*myDetailPadding);
		inputField.setMaxWidth(myPaneWidth-4*myDetailPadding);
		inputField.setMinHeight(cbHeight);
		inputField.setMaxHeight(cbHeight);
		inputField.setOnMouseClicked(e -> handleClick(inputField));
		myPropertiesVBox.getChildren().add(inputField);
	}
	
	public void createProperties(){
		String [] array = DetailResources.PROPERTIES.getArrayResource();
		for (String label : array){
			BorderPane bp = new BorderPane();
			bp.setMinWidth(myPaneWidth-4*myDetailPadding);
			bp.setMaxWidth(myPaneWidth-4*myDetailPadding);
			Label labl = createPropertyLbl(label);
			ComboBox<String> cb = createPropertyCB(label);
			bp.setLeft(labl);
			bp.setRight(cb);
			bp.setAlignment(labl, Pos.CENTER_LEFT);
			myPropertiesVBox.getChildren().add(bp);
		}
	}
	
	public Label createPropertyLbl(String property){
		Label labl = new Label (property);
		return labl;
	}
	
	public ComboBox<String> createPropertyCB(String property){
		DetailResources resourceChoice = DetailResources.valueOf(property.toUpperCase(Locale.ENGLISH));
		String [] optionsArray = resourceChoice.getArrayResource();
		ComboBox<String> cb = createComboBox(optionsArray);
		return cb;
	}
	
	public TextArea createInputField(String label, double hboxSpacing){
		TextArea inputField = new TextArea(label);
		inputField.setMinWidth(myPaneWidth-2*myDetailPadding-cbWidth-hboxSpacing);
		inputField.setMaxWidth(myPaneWidth-2*myDetailPadding-cbWidth-hboxSpacing);
		inputField.setMinHeight(cbHeight);
		inputField.setMaxHeight(cbHeight);
		inputField.setOnMouseClicked(e -> handleClick(inputField));
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

	public void createTextField(){
		
	}

}
