package gameeditor.commanddetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import frontend.util.GameEditorException;
import gameeditor.controller.interfaces.IGameEditorData;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
/**
 * @author John Martin, Pratiksha Sharma
 * 
 *
 */
public class SpriteDetail {
    private IGameEditorData myDataStore;
    private VBox myVBox;
    private TextArea myTypeTextArea;
    private DetailFrontEndUtil myDetailFrontEndUtil;      
    private ImageDetail myImageDetail;
    private String myImageFilePath="";
    private String [] myPropertiesComBoArray = DetailResources.PROPERTIES_COMBO.getArrayResource();
    private String[] myPropertiesTextBox = DetailResources.SPRITE_PROPERTIES_TEXT_INPUT_LABEL.getArrayResource();
    private String[] myPropertiesComboKeys = DetailResources.PROPERTIES_COMBO_KEYS.getArrayResource();
    private String[] SPRITE_DEFAULT_COMBO_PROPERTIES = DetailDefaultsResources.SPRITE_DEFAULT_COMBO_PROPERTIES.getArrayResource();
    private ArrayList<TextArea> myTextFields = new ArrayList<TextArea>();
    private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private BorderPane myNonIntersectableOptionBP;
    private BorderPane myIntersectableBP;
    private ComboBox<String> nonInterSectableCombo;
    public static final String [] PLATFORM_INTERSECTABLE_OPTIONS = DetailResources.PLATFORM_INTERSECTABLE_OPTIONS.getArrayResource();
    public static final String PLATFORM_NON_INTERSECTIBLE_LABEL = DetailResources.PLATFORM_NON_INTERSECTIBLE_LABEL.getResource();
    public static final String[] PLATFORM_NON_INTERSECTABLE_OPTIONS = DetailResources.PLATFORM_NON_INTERSECTABLE_OPTIONS.getArrayResource();



    public SpriteDetail(IGameEditorData dataStore){
        this.myDataStore =dataStore;
        myDetailFrontEndUtil= new DetailFrontEndUtil();
        myImageDetail = new ImageDetail();  
    }

    public VBox getTabContent(){
        myVBox = new VBox();
        myVBox.setSpacing(IAbstractCommandDetail.MY_DETAIL_PADDING);
        myVBox.setAlignment(Pos.CENTER);
        myTypeTextArea=myDetailFrontEndUtil.createTypeName();
        myVBox.getChildren().add(myTypeTextArea);
        createProperties();
        myVBox.getChildren().addAll(myImageDetail.createImageChoose());
        createSave(e-> handleSaveSprite()); 
        
        return myVBox;
    }


    private void createProperties(){
        int counter=0;
        for (String label : myPropertiesComBoArray){
            ComboBox<String> cb = myDetailFrontEndUtil.createPropertyCB(label,SPRITE_DEFAULT_COMBO_PROPERTIES[counter]);
            myComboBoxes.add(cb);
            BorderPane bp = myDetailFrontEndUtil.createBorderpane(cb,myDetailFrontEndUtil.createPropertyLbl(label));
            myVBox.getChildren().add(bp);
            counter++;
        }
        
        String defaultProperty = DetailDefaultsResources.PLATFORM_NON_INTERSECTABLE.getResource();
        ComboBox<String> intersectable = myDetailFrontEndUtil.createComboBox(PLATFORM_INTERSECTABLE_OPTIONS, defaultProperty);
        intersectable.setOnAction(e -> handleIntersectibleProperty(intersectable));
        myIntersectableBP = myDetailFrontEndUtil.createBorderpane(intersectable,(myDetailFrontEndUtil.createPropertyLbl(PLATFORM_NON_INTERSECTIBLE_LABEL)));
        myVBox.getChildren().add(myIntersectableBP);
    
        for (String label : myPropertiesTextBox){           
            TextArea text = myDetailFrontEndUtil.createInputField(DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource());
            myTextFields.add(text);
            BorderPane bp = myDetailFrontEndUtil.createBorderpane(text,myDetailFrontEndUtil.createPropertyLbl(label));
            myVBox.getChildren().add(bp);
        }
}


    private void handleIntersectibleProperty(ComboBox<String> combo){
        if(combo.getValue()=="False"){
            String defaultVal = DetailDefaultsResources.PLATFORM_INTERSECTABLE.getResource();
            nonInterSectableCombo = myDetailFrontEndUtil.createComboBox(PLATFORM_NON_INTERSECTABLE_OPTIONS, defaultVal);
            String label = DetailResources.NON_INTERSECTABLE_SIDES_LABEL.getResource();
            myNonIntersectableOptionBP = myDetailFrontEndUtil.createBorderpane( nonInterSectableCombo,myDetailFrontEndUtil.createPropertyLbl(label));
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

    private void handleSaveSprite(){
        if(verifySave()){
            Map<String, String> propertiesMap = new HashMap<String, String>();
            getPropertiesFromCombo(propertiesMap);
            getPropertiesFromTextArea(propertiesMap);
            propertiesMap.put(DetailResources.TYPE_NAME.getResource(), myTypeTextArea.getText());
            myImageFilePath = myImageDetail.getFilePath();
            
            propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), myImageFilePath);
            // Add Enemy Properties for the other sprites
            propertiesMap.put(DetailResources.ENEMY_KEY.getResource(), null);

            if(myDataStore.getType(myTypeTextArea.getText())==null){
                myDataStore.storeType(propertiesMap);
            }else{
                GameEditorException e = new GameEditorException();
                e.showError("Type Name already exists");
            }
        }else{

        }      
    }

    private void getPropertiesFromCombo(Map<String,String> propertiesMap){
        int counter =0;
        for(String label: myPropertiesComboKeys){
            if(myComboBoxes.get(counter).getValue()!=null){
                if(label.equals(DetailResources.SCROLLABLE_LABEL.getResource())&& myComboBoxes.get(counter).getValue().equals("True")){
                   // Dont Save 
                }else 
                propertiesMap.put(label,myComboBoxes.get(counter).getValue()); 
            }
            counter++;
        }
    }

    private void getPropertiesFromTextArea(Map<String,String> propertiesMap){
        int i=0;
        for(String labl: myPropertiesTextBox){
            if(!myTextFields.get(i).getText().isEmpty()){ 
                
                if(!labl.equals(DetailResources.MAIN_CHAR_MOVEMENT_LABEL.getResource())){
                    labl = DetailResources.MAIN_CHAR_MOVEMENT_KEY.getResource();
                }
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
}
