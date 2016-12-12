package gameeditor.rpg.commanddetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gameeditor.rpg.GameObjectView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;


// TODO: Refactor this class - duplicated code with CreateDetail
public class MainCharacterDetail extends AbstractSelectDetail {
    private List<String> myPropertiesArray = Arrays.asList(DetailResources.MAIN_CHARACTER_PROPERTIES.getArrayResource());
    private List<TextArea> myTextInputs = new ArrayList<TextArea>();
    public static final double MAIN_CHARACTER_INITIAL_X_POSITION = 20;
    public static final double MAIN_CHARACTER_INITIAL_Y_POSITION = 20;
    public static final double MAIN_CHARACTER_HEIGHT = 100;
    public static final double MAIN_CHARACTER_WIDTH = 100;

    private  String imageViewString;
    private Map<String,String> myMainCharMap;

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
                    myMainCharMap.put(label.toLowerCase(), myTextInputs.get(i).getText()); 
                }
                i++;
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
    }

    private BorderPane addOptions(String label, String value){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(PADDED_PANE_WIDTH);
        bp.setMaxWidth(PADDED_PANE_WIDTH);
        Label labl = createPropertyLbl(label);
        TextArea text = createInputField(value);
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
