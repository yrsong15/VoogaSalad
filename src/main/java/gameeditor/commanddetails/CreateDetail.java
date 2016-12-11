package gameeditor.commanddetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CreateDetail extends AbstractCommandDetail {
    private String myFilePath = "";
    //private Pane myImagePane;
    private TextArea myTypeTextArea;
    private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String [] myPropertiesComBoArray = DetailResources.PROPERTIES_COMBO.getArrayResource();
    private String[] myPropertiesTextBox = DetailResources.SPRITE_PROPERTIES_TEXT_INPUT_LABEL.getArrayResource();
    private String[] myPropertiesComboLabels = DetailResources.PROPERTIES_COMBO_LABELS.getArrayResource();
    private ArrayList<TextArea> myTextFields = new ArrayList<TextArea>();
   
    
    
    private TabPane myTabPane;
    private Tab mySpriteTab;
    private Tab myPlatformTab;

    private ImageDetail myImageDetail;
    private BorderPane myNonIntersectableOptionBP;
    private BorderPane myIntersectableBP;
    private ComboBox<String> nonInterSectableCombo;
   
    public CreateDetail() {
        super();
    }

    @Override
    public void init() {      
        myTabPane = new TabPane();
        myImageDetail = new ImageDetail();
        createSpriteTab();
        createPlatformTab();
        myTabPane.getTabs().addAll(mySpriteTab, myPlatformTab);
        myContainerPane.setContent(myTabPane);
    }

    private void createSpriteTab(){
        mySpriteTab = new Tab("Sprite"); 
        mySpriteTab.setOnSelectionChanged(e-> setSpriteTab());
    }

    private void createPlatformTab(){
        myPlatformTab = new Tab(PLATFORM_LABEL);
        myPlatformTab.setOnSelectionChanged(e -> setPlatformTab());  
    }

    private void setSpriteTab(){
        if(mySpriteTab.isSelected()){  
            myVBox = new VBox();
            myVBox.setSpacing(MY_DETAIL_PADDING);
            myVBox.setAlignment(Pos.CENTER);
            createTypeName();
            createProperties();
            myVBox.getChildren().add(myImageDetail.createImageChoose());
            createSave(e-> handleSaveSprite()); 
            mySpriteTab.setContent(myVBox);
        }
    }

    private void setPlatformTab(){
        if(myPlatformTab.isSelected()){
            myVBox = new VBox();
            myVBox.setSpacing(MY_DETAIL_PADDING);
            createTypeName();
            createPlatformProperties();
            myVBox.getChildren().add(myImageDetail.createImageChoose());
            myPlatformTab.setContent(myVBox);
            createSave(e-> handleSavePlatform()); 
        }
    }
    
    private void createPlatformProperties(){
        String defaultProperty = DetailDefaultsResources.PLATFORM_NON_INTERSECTABLE.getResource();
       ComboBox<String> intersectable = myDetailFrontEndUtil.createComboBox(PLATFORM_INTERSECTABLE_OPTIONS, defaultProperty);
    
       intersectable.setOnAction(e -> handleIntersectibleProperty(intersectable));
       myIntersectableBP = myDetailFrontEndUtil.createBorderpane(intersectable,(createPropertyLbl(PLATFORM_NON_INTERSECTIBLE_LABEL)));
       myVBox.getChildren().add(myIntersectableBP);
    }
    
    private void handleIntersectibleProperty(ComboBox<String> combo){
        if(combo.getValue()=="False"){
            String defaultVal = DetailDefaultsResources.PLATFORM_NON_INTERSECTABLE.getResource();
            nonInterSectableCombo = myDetailFrontEndUtil.createComboBox(PLATFORM_NON_INTERSECTABLE_OPTIONS, defaultVal);
            String label = DetailResources.NON_INTERSECTABLE_SIDES_LABEL.getResource();
            myNonIntersectableOptionBP = myDetailFrontEndUtil.createBorderpane( nonInterSectableCombo,createPropertyLbl(label));
            int index = myVBox.getChildren().indexOf(myIntersectableBP);
            myVBox.getChildren().add(index+1, myNonIntersectableOptionBP);
         
        } else if((combo.getValue().equals("True"))){
                if(myVBox.getChildren().contains(myNonIntersectableOptionBP)){
                    myVBox.getChildren().remove(myVBox.getChildren().indexOf(myNonIntersectableOptionBP));
                }
            }
    }

    private void createSave(EventHandler<MouseEvent> handler){
        Button save = myDetailFrontEndUtil.createButton("SaveCommand",handler);
        myVBox.getChildren().add(save);
    }

    private void handleSavePlatform(){
        if(myDataStore.getType(myTypeTextArea.getText())==null){
            Map<String,String> propertiesMap = new HashMap<String,String>();
            getPlatFormProperties(propertiesMap);
            propertiesMap.put(DetailResources.TYPE_NAME.getResource(),myTypeTextArea.getText());
            myFilePath = myImageDetail.getFilePath();
            propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), myFilePath);
            
            for(String key: propertiesMap.keySet()){
                System.out.println(" Key: " + key + " Value: " +propertiesMap.get(key));
            }
            myDataStore.storeType(propertiesMap);
        }
    }
    

    private void getPlatFormProperties(Map<String,String> propertiesMap){
        if(nonInterSectableCombo!=null){
            if(nonInterSectableCombo.getValue().toString().equals("Both")){
                propertiesMap.put(DetailResources.NON_INTERSECTABLE_KEY.getResource(), "True");
            }else {
            propertiesMap.put(DetailResources.ONE_SIDE_NON_INTERSECTABLEKEY.getResource(), nonInterSectableCombo.getValue().toString());
        }
        }
        
    }

    private void handleSaveSprite(){
        if (verifySave()){
            Map<String, String> propertiesMap = new HashMap<String, String>();
            getPropertiesFromCombo(propertiesMap);
            getPropertiesFromTextArea(propertiesMap);
            propertiesMap.put(DetailResources.TYPE_NAME.getResource(), myTypeTextArea.getText());
            myFilePath = myImageDetail.getFilePath();
            propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), myFilePath);
            // Add Enemy Properties for the other sprites
            propertiesMap.put(DetailResources.ENEMY_KEY.getResource(), null);
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
            BorderPane bp = myDetailFrontEndUtil.createBorderpane(cb,createPropertyLbl(label));
            myVBox.getChildren().add(bp);
        }

        for (String label : myPropertiesTextBox){           
            TextArea text = createInputField("0.0");
            myTextFields.add(text);
            BorderPane bp = myDetailFrontEndUtil.createBorderpane(text,createPropertyLbl(label));
            myVBox.getChildren().add(bp);
        }
    }

    private ComboBox<String> createPropertyCB(String property){
        DetailResources resourceChoice = DetailResources.valueOf(property.toUpperCase(Locale.ENGLISH));
        String [] optionsArray = resourceChoice.getArrayResource();
        ComboBox<String> cb = myDetailFrontEndUtil.createComboBox(optionsArray,null);
        return cb;
    }
}
