package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gameeditor.objects.GameObjectView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

// TODO: Refactor this class - duplicated code with CreateDetail
public class MainCharacterDetail extends AbstractSelectDetail {

	
    //private VBox myVBox;
    //private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String [] myPropertiesArray = DetailResources.MAIN_CHARACTER_PROPERTIES.getArrayResource();
    private List<TextArea> myTextInputs = new ArrayList<TextArea>();
    public static final double MAIN_CHARACTER_INITIAL_X_POSITION = 20;
    public static final double MAIN_CHARACTER_INITIAL_Y_POSITION = 20;
    public static final double MAIN_CHARACTER_HEIGHT = 100;
    public static final double MAIN_CHARACTER_WIDTH = 100;

    private DetailFrontEndUtil myDetailFrontEndUtil;

    public MainCharacterDetail() {
        super();
        myDetailFrontEndUtil = new DetailFrontEndUtil();
    }


    @Override
    public void init() {
        addVBoxSettings();
        createProperties();
        createSave();
        myDesignArea.enableClick(this);
     
    }

    @Override
    public void initLevel2(GameObjectView sprite){

        
        myGO = sprite;
        addVBoxSettings();
        createPos();
        createProperties();
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
            // TODO: Create this for saving maincharacter
            // TODO: Input file path when creating pane
            //ResourceBundle geprops =  ResourceBundle.getBundle("GameEditorProperties");
            //Enumeration<String> enumKeys = geprops.getKeys();
            //String [] propertiesList = DetailResources.PROPERTIES_TEXT_INPUT.getArrayResource();
            Map<String, String> propertiesMap = new HashMap<String, String>();

            int i=0;
            for(String label: myPropertiesArray){
                if(!myTextInputs.get(i).getText().isEmpty()){
                    propertiesMap.put(label.toLowerCase(), myTextInputs.get(i).getText()); 
                }
                i++;
            }  

            // Add main character in the back end here 
           // myDataStore.addMainCharacter(MAIN_CHARACTER_INITIAL_X_POSITION, MAIN_CHARACTER_INITIAL_Y_POSITION, 
                                        // MAIN_CHARACTER_WIDTH, MAIN_CHARACTER_HEIGHT, propertiesMap);
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

    public void createProperties(){
        for (String label : myPropertiesArray){  
            myVBox.getChildren().add(addOptions(label));
        }
    }

    private BorderPane addOptions(String label){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(PADDED_PANE_WIDTH);
        bp.setMaxWidth(PADDED_PANE_WIDTH);
        Label labl = createPropertyLbl(label);
        TextArea text = createInputField("0.0");
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
