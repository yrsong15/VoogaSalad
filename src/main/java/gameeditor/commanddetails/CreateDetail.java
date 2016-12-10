package gameeditor.commanddetails;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import frontend.util.FileOpener;
import gameeditor.view.ViewResources;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CreateDetail extends AbstractCommandDetail {
    private String myFilePath = "";
    private Pane myImagePane;
    private TextArea myTypeTextArea;
    private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String [] myPropertiesComBoArray = DetailResources.PROPERTIES_COMBO.getArrayResource();
    private String[] myPropertiesTextBox = DetailResources.SPRITE_PROPERTIES_TEXT_INPUT_LABEL.getArrayResource();
    private String[] myPropertiesComboLabels = DetailResources.PROPERTIES_COMBO_LABELS.getArrayResource();
    private ArrayList<TextArea> myTextFields = new ArrayList<TextArea>();


    public CreateDetail() {
        super();
    }

    @Override
    public void init() {
        addVBoxSettings();
        createTypeName();
        createProperties();
        createImageChoose();
        createSave();
    }

    private void createSave(){
        Button save = createButton("SaveCommand",e -> handleSave());
        myVBox.getChildren().add(save);
    }

    private void handleSave(){
        if (verifySave()){
            Map<String, String> propertiesMap = new HashMap<String, String>();
            getPropertiesFromCombo(propertiesMap);
            getPropertiesFromTextArea(propertiesMap);
            
            propertiesMap.put(DetailResources.TYPE_NAME.getResource(), myTypeTextArea.getText());
            propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), myFilePath);

            // Store only if the Type does not exist
            if(myDataStore.getType(myTypeTextArea.getText())==null){
                myDataStore.storeType(propertiesMap);
            }
            // myDataStore.addGameObjectToLevel(propertiesMap);
        }   else {
        }
    }

    private void getPropertiesFromCombo(Map<String,String> propertiesMap){
        int counter =0;
        for(String label: myPropertiesComboLabels){
            if(myComboBoxes.get(counter).getValue()!=null){
                propertiesMap.put(label,myComboBoxes.get(counter).getValue()); 
            }
            counter++;
        }
    }
    
    private void getPropertiesFromTextArea(Map<String,String> propertiesMap){
        int i=0;
        for(String labl: myPropertiesTextBox){
            if(!myTextFields.get(i).getText().isEmpty()){
                propertiesMap.put(labl.toLowerCase(), myTextFields.get(i).getText());  
                i++;
            }
        }
    }

    private boolean verifySave(){
        // TODO: FINISH VERIFICATION METHOD
        // Check all of the following:
        // Type Name != null or TypeName or ""
        // Destructible/Damage/Points/Time/Random/Health/Movable != null
        // SpriteImage != null/unfindable
        return true;
    }

    private void createImageChoose(){
        myImagePane = new Pane();
        myImagePane.setMinWidth(60);
        myImagePane.setMaxWidth(60);
        Button choose = createImageButton();
        Rectangle imageZone = new Rectangle(DetailResources.TYPE_IMAGE_ZONE_WIDTH.getDoubleResource(), DetailResources.TYPE_IMAGE_ZONE_HEIGHT.getDoubleResource(), Color.GHOSTWHITE);
        myImagePane.getChildren().add(imageZone);
        imageZone.setArcHeight(DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource()); imageZone.setArcWidth(DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource());
        BorderPane bp = createBorderpane(myImagePane,choose);
        myVBox.getChildren().add(bp);
    }

    private void createImageView(){
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

    private Button createImageButton(){
       return createButton("ChooseImageCommand", e-> createImageView());
    }

    private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file =(myFileOpener.chooseFile(fileType, fileLocation));
        if(file !=null){
            return file.toURI().toString();
        }
        return null;
    }

    private void createTypeName(){
        myTypeTextArea = new TextArea();
        myTypeTextArea = new TextArea(DetailResources.TYPE_NAME.getResource());
        myTypeTextArea.setMinWidth(PADDED_PANE_WIDTH);
        myTypeTextArea.setMaxWidth(PADDED_PANE_WIDTH);
        myTypeTextArea.setMinHeight(CB_HEIGHT);
        myTypeTextArea.setMaxHeight(CB_HEIGHT);
        myTypeTextArea.setOnMouseClicked(e -> handleClick(myTypeTextArea));
        myVBox.getChildren().add(myTypeTextArea);
    }

    private void createProperties(){
        for (String label : myPropertiesComBoArray){
            ComboBox<String> cb = createPropertyCB(label);
            myComboBoxes.add(cb);
            BorderPane bp = createBorderpane(cb,createPropertyLbl(label));
            myVBox.getChildren().add(bp);
        }

        for (String label : myPropertiesTextBox){           
            TextArea text = createInputField("0.0");
            myTextFields.add(text);
            BorderPane bp = createBorderpane(text,createPropertyLbl(label));
            myVBox.getChildren().add(bp);
        }
    }

    private ComboBox<String> createPropertyCB(String property){
        DetailResources resourceChoice = DetailResources.valueOf(property.toUpperCase(Locale.ENGLISH));
        String [] optionsArray = resourceChoice.getArrayResource();
        ComboBox<String> cb = createComboBox(optionsArray);
        return cb;
    }

 
}
