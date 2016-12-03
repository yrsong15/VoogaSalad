package gameeditor.commanddetails;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import frontend.util.FileOpener;
import gameeditor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CreateDetail extends AbstractCommandDetail {

    private String myFilePath = "";
    private VBox myVBox;
    private Pane myImagePane;
    private TextArea myTypeTextArea;
    private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String [] myPropertiesComBoArray = DetailResources.PROPERTIES_COMBO.getArrayResource();
    private String[] myPropertiesTextBox = DetailResources.PROPERTIES_TEXT_INPUT.getArrayResource();
    private String[] myPropertiesComboLabels = DetailResources.PROPERTIES_COMBO_LABELS.getArrayResource();
    private ArrayList<TextArea> myTextFields = new ArrayList<TextArea>();
  

    public CreateDetail() {
        super();
    }


    @Override
    public void init() {
        myVBox = new VBox();
        myVBox.setSpacing(myDetailPadding);
        myVBox.setAlignment(Pos.CENTER);
        myContainerPane.setContent(myVBox);	
        createTypeName();
        createProperties();
        createImageChoose();
        createSave();
    }

    public void createSave(){
        Button save = new Button();
        save.setText("Save New Type");
        save.setMinWidth(cbWidth);
        save.setMinHeight(cbHeight);
        save.setOnAction((e) -> {handleSave();});
        myVBox.getChildren().add(save);
    }

    public void handleSave(){
        if (verifySave()){
            //            ResourceBundle geprops =  ResourceBundle.getBundle("GameEditorProperties");
            //            Enumeration<String> enumKeys = geprops.getKeys();

            Map<String, String> propertiesMap = new HashMap<String, String>();

            int j=0;
            for(String label: myPropertiesComboLabels){
                if(myComboBoxes.get(j).getValue()!=null){
                    propertiesMap.put(label,myComboBoxes.get(j).getValue()); 
                }
                j++;;
            }
            
            int i=0;
            for(String labl: myPropertiesTextBox){
                if(!myTextFields.get(i).getText().isEmpty()){
                    propertiesMap.put(labl.toLowerCase(), myTextFields.get(i).getText());  
                    i++;
                }
            }

            propertiesMap.put(DetailResources.TYPE_NAME.getResource(), myTypeTextArea.getText());
            propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), myFilePath);
            
            myDataStore.storeType(propertiesMap);



        }   else {

        }
    
}


public boolean verifySave(){
    // TODO: FINISH VERIFICATION METHOD
    // Check all of the following:
    // Type Name != null or TypeName or ""
    // Destructible/Damage/Points/Time/Random/Health/Movable != null
    // SpriteImage != null/unfindable
    return true;
}

public void createImageChoose(){
    BorderPane bp = new BorderPane();
    bp.setMinWidth(paddedPaneWidth);
    bp.setMaxWidth(paddedPaneWidth);
    myImagePane = new Pane();
    myImagePane.setMinWidth(60);
    myImagePane.setMaxWidth(60);
    Button choose = createImageButton();
    Rectangle imageZone = new Rectangle(DetailResources.TYPE_IMAGE_ZONE_WIDTH.getDoubleResource(), DetailResources.TYPE_IMAGE_ZONE_HEIGHT.getDoubleResource(), Color.GHOSTWHITE);
    myImagePane.getChildren().add(imageZone);
    imageZone.setArcHeight(DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource()); imageZone.setArcWidth(DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource());
    bp.setLeft(choose);
    bp.setRight(myImagePane);
    BorderPane.setAlignment(choose, Pos.CENTER_LEFT);
    BorderPane.setAlignment(myImagePane, Pos.CENTER_RIGHT);
    myVBox.getChildren().add(bp);
}

public void createImageView(){
    myFilePath = getFilePath(ViewResources.IMAGE_FILE_TYPE.getResource(), ViewResources.SPRITE_IMAGE_LOCATION.getResource());       
    Image i = new Image(myFilePath);
    double imageZonePadding = DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource();
    double imageZoneWidth = DetailResources.TYPE_IMAGE_ZONE_WIDTH.getDoubleResource();
    double imageZoneHeight = DetailResources.TYPE_IMAGE_ZONE_HEIGHT.getDoubleResource();
    double fitWidth = imageZoneWidth-imageZonePadding;
    double fitHeight = imageZoneHeight-imageZonePadding;
    double widthRatio = fitWidth/i.getWidth();
    double heightRatio = fitHeight/i.getHeight();
    double ratio = Math.min(widthRatio, heightRatio);
    double endWidth = i.getWidth()*ratio;
    double endHeight = i.getHeight()*ratio;
    ImageView iv = new ImageView(i);
    iv.setFitWidth(fitWidth);
    iv.setFitHeight(fitHeight);
    iv.setPreserveRatio(true);
    iv.setLayoutX(imageZoneWidth/2 - endWidth/2);
    iv.setLayoutY(imageZoneHeight/2 - endHeight/2);
    iv.setLayoutX(imageZonePadding/2); iv.setLayoutY(imageZonePadding/2);
    myImagePane.getChildren().add(iv);
    //System.out.println(myImagePane.getChildren());
}

public Button createImageButton(){
    Button choose = new Button();
    choose.setText("Select Sprite Image");
    choose.setMinWidth(cbWidth);
    choose.setMinHeight(cbHeight);
    choose.setOnAction((e) -> {createImageView();});
    return choose;
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
    myTypeTextArea = new TextArea(DetailResources.TYPE_NAME.getResource());
    myTypeTextArea.setMinWidth(paddedPaneWidth);
    myTypeTextArea.setMaxWidth(paddedPaneWidth);
    myTypeTextArea.setMinHeight(cbHeight);
    myTypeTextArea.setMaxHeight(cbHeight);
    myTypeTextArea.setOnMouseClicked(e -> handleClick(myTypeTextArea));
    myVBox.getChildren().add(myTypeTextArea);
}

public void createProperties(){
    for (String label : myPropertiesComBoArray){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(paddedPaneWidth);
        bp.setMaxWidth(paddedPaneWidth);
        Label labl = createPropertyLbl(label);

        ComboBox<String> cb = createPropertyCB(label);

        myComboBoxes.add(cb);

        bp.setLeft(labl);
        bp.setRight(cb);
        BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
        myVBox.getChildren().add(bp);
    }

    for (String label : myPropertiesTextBox){           
        BorderPane bp = new BorderPane();
        bp.setMinWidth(paddedPaneWidth);
        bp.setMaxWidth(paddedPaneWidth);
        Label labl = createPropertyLbl(label);
        TextArea text = createInputField();

        myTextFields.add(text);

        bp.setLeft(labl);
        bp.setRight(text);
        BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
        myVBox.getChildren().add(bp);
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

public TextArea createInputField(){
    TextArea inputField = new TextArea();
    inputField.setMinWidth(paddedDetailWidth);
    inputField.setMaxWidth(paddedDetailWidth);
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
