package gameeditor.commanddetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import gameeditor.objects.GameObjectView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
/**
 * @author John Martin
 * Modified by Pratiksha Sharma
 */
public class ControlsDetail extends AbstractCommandDetail implements ILevelTwo {
    private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String[] myControlsOptions = DetailResources.CONTROL_OPTIONS.getArrayResource();
    private ArrayList<TextArea> myInputFields = new ArrayList<TextArea>();
    private GameObjectView myGO;
    private String myImageViewString;
    private Map<String,String> myMainCharMap;


    public ControlsDetail() {
        super();
    }

    @Override
    public void init() {
        addVBoxSettings();
        initLevel2(myDetailPane.getCurrentAvatar());
        int counter=0;
        for (@SuppressWarnings("unused") String string : myControlsOptions){
            String defaultKey = null;
            String defaultControl = null;
            KeyCode[] keys = new KeyCode[myControlsOptions.length];
            String[] values = new String[myControlsOptions.length];
           
            ResourceBundle controlProps =  ResourceBundle.getBundle("ControlKeys",Locale.getDefault());
            if(myGO!=null){
                if(myDataStore.getControlsMap(myGO.getType())!=null){
                    Map<KeyCode,String> map = myDataStore.getControlsMap(myGO.getType());
                    
                    map.keySet().toArray(keys);
                    map.values().toArray(values); 

                    defaultKey = keys[counter].toString();
                    defaultControl = controlProps.getString(values[counter]);
                    
                }else{

                    defaultKey = DetailDefaultsResources.CONTROLS_DEFAULT_VALUES.getArrayResource()[counter];
                    defaultControl = DetailDefaultsResources.CONTROLS_DEFAULT_COMBO_OPTIONS.getArrayResource()[counter];
                }
            }
                createSelectDirectionsControl(defaultKey, myControlsOptions,defaultControl);
                counter++;        
        }
        createSave();  
    }

    @Override
    public void initLevel2 (GameObjectView sprite) {
        if(sprite!=null){
            myGO = sprite;
            myImageViewString = myGO.getImageView().toString();
            myMainCharMap = myDataStore.getMainCharMap(myImageViewString);  
        }
    }

    private void createSave(){
        Button save = myDetailFrontEndUtil.createButton("SaveCommand",e -> handleSave());
        myVBox.getChildren().add(save);
    }

    private void handleSave(){
        ResourceBundle controlProps =  ResourceBundle.getBundle("ControlKeys");
        Map<KeyCode,String> controlMap = new HashMap<KeyCode,String>();
        for (int i = 0; i < myComboBoxes.size(); i++){
            String controlKey = myComboBoxes.get(i).getValue();
            String kcString = myInputFields.get(i).getText();
            if ( controlKey != null && kcString != null && kcString !="Input" && !kcString.isEmpty()){
                KeyCode kc = KeyCode.valueOf(kcString);
                controlMap.put(kc, controlProps.getString(controlKey.toLowerCase()));
                
            }
        }      
        // Store in the DataStore
        String typeName = myMainCharMap.get(DetailResources.TYPE_NAME.getResource());
        myDataStore.addControls(typeName, controlMap); 

    }

    private void createSelectDirectionsControl(String label, String[] optionsArray, String comboDefault){
        HBox innerContainer = new HBox();
        innerContainer.setSpacing(HBOX_SPACING);
        innerContainer.setAlignment(Pos.CENTER);
        ComboBox<String> cb = myDetailFrontEndUtil.createComboBox(optionsArray,comboDefault);
        TextArea inputField = createControlsInput(label);
        myInputFields.add(inputField);
        myComboBoxes.add(cb);
        innerContainer.getChildren().add(cb);
        innerContainer.getChildren().add(inputField);
        myVBox.getChildren().add(innerContainer);
    }

    private TextArea createControlsInput(String label){
        TextArea inputField = myDetailFrontEndUtil.createInputField(label);
        inputField.setOnKeyPressed(e -> inputField.clear());
        inputField.setOnKeyReleased(e -> handleKey(inputField, e.getCode()));
        return inputField;
    }

    private void handleKey(TextArea field, KeyCode kc){
        field.setText(kc.toString());
    }
   
}
