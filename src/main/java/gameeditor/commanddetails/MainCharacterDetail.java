package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gameeditor.objects.GameObjectView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
/**
 * @author John Martin, Pratiksha Sharma
 *
 */
public class MainCharacterDetail extends AbstractSelectDetail {
    private List<String> myPropertiesArray = Arrays.asList(DetailResources.MAIN_CHARACTER_PROPERTIES.getArrayResource());
    private List<TextArea> myTextInputs = new ArrayList<TextArea>();
    public static final double MAIN_CHARACTER_INITIAL_X_POSITION = 20;
    public static final double MAIN_CHARACTER_INITIAL_Y_POSITION = 20;
    public static final double MAIN_CHARACTER_HEIGHT = 100;
    public static final double MAIN_CHARACTER_WIDTH = 100;

    private  String imageViewString;
    private Map<String,String> myMainCharMap;
    private ComboBox<String> jumpCombo;

    public MainCharacterDetail() {
        super();
        myDetailFrontEndUtil = new DetailFrontEndUtil();
    }


    @Override
    public void init() {
        addVBoxSettings();
        createSave();
        myDesignArea.enableClick(this);
    }

    @Override
    public void initLevel2(GameObjectView sprite){
        myGO = sprite;
        imageViewString = myGO.getImageView().toString();
        myMainCharMap = myDataStore.getMainCharMap(imageViewString);  
        addVBoxSettings();
        createPos();
        addProperties();
        //TODO: switch to character in detail pane
        createSave();
    }

    private void createSave(){
        Button save = new Button();
        save.setText("Save New Type");
        save.setMinWidth(CB_WIDTH);
        save.setMinHeight(CB_HEIGHT);
        save.setOnMouseClicked((e) -> {handleSave();});
        myVBox.getChildren().add(save);
    }

    private void handleSave(){
        if (verifySave()){
            int i=0;
            for(String label: myPropertiesArray){
                boolean containsDefault= myTextInputs.get(i).getText().equals(DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource());
                if(!(myTextInputs.get(i).getText().isEmpty()) &&(!containsDefault )){
                    if(!label.equals(DetailResources.MAIN_CHAR_MOVEMENT_LABEL.getResource())){
                        label = DetailResources.MAIN_CHAR_MOVEMENT_KEY.getResource();
                    }
                    myMainCharMap.put(label.toLowerCase(), myTextInputs.get(i).getText()); 
                }
                i++;
            }
            
            if(jumpCombo.getValue()!=null){
                String value = jumpCombo.getValue().toLowerCase();
                value = value.replaceAll("\\s+","");
                myMainCharMap.put(value, myMainCharMap.get("jump"));
                myMainCharMap.remove("jump");
            }

        } else {

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

    private void addProperties(){
        myPropertiesArray.forEach(label -> {
          if(myMainCharMap!=null && myMainCharMap.containsKey(label.toLowerCase())){
              myVBox.getChildren().add(addOptions(label,myMainCharMap.get(label.toLowerCase())));
          }else{
            myVBox.getChildren().add(addOptions(label,DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource()));
        }}); 
        
         addjumpTypeCombo();
        
    }

    private void addjumpTypeCombo(){
        String[] options = DetailResources.JUMP_OPTIONS.getArrayResource();
        jumpCombo = myDetailFrontEndUtil.createComboBox(options, options[0]);
        jumpCombo.setMaxWidth(IAbstractCommandDetail.CB_WIDTH*1.3);
        jumpCombo.setMinWidth(IAbstractCommandDetail.CB_WIDTH*1.3);
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(jumpCombo,myDetailFrontEndUtil.createPropertyLbl("Jump Type"));
        myVBox.getChildren().add(bp);
    }
    
    private BorderPane addOptions(String label, String value){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(PADDED_PANE_WIDTH);
        bp.setMaxWidth(PADDED_PANE_WIDTH);
        Label labl = myDetailFrontEndUtil.createPropertyLbl(label);
        TextArea text = myDetailFrontEndUtil.createInputField(value);
        myTextInputs.add(text);
        bp.setLeft(labl);
        bp.setRight(text);
        BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
        return bp;
    }

    @Override
    public void switchSelectStyle(GameObjectView sprite) {
        if (!sprite.getIsMainChar()){
            myDetailPane.setDetail("Select");
        }
    }
}
