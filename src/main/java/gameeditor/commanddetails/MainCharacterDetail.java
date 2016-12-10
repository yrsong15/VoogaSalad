package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import objects.GameObject;

// TODO: Refactor this class - duplicated code with CreateDetail
public class MainCharacterDetail extends AbstractCommandDetail {

    //private VBox myVBox;
    //private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String [] myPropertiesArray = DetailResources.MAIN_CHARACTER_PROPERTIES.getArrayResource();
    private List<TextArea> myTextInputs = new ArrayList<TextArea>();
    public static final double MAIN_CHARACTER_INITIAL_X_POSITION = 20;
    public static final double MAIN_CHARACTER_INITIAL_Y_POSITION = 20;
    public static final double MAIN_CHARACTER_HEIGHT = 100;
    public static final double MAIN_CHARACTER_WIDTH = 100;

    public MainCharacterDetail() {
        super();
    }


    @Override
    public void init() {
//        myVBox = new VBox();
//        myVBox.setSpacing(MY_DETAIL_PADDING);
//        myVBox.setAlignment(Pos.CENTER);
//        myContainerPane.setContent(myVBox);	
        addVBoxSettings();
        createProperties();
        createSave();
    }

    public void createSave(){
        Button save = new Button();
        save.setText("Save New Type");
        save.setMinWidth(CB_WIDTH);
        save.setMinHeight(CB_HEIGHT);
        save.setOnMouseClicked((e) -> {handleSave();});
        myVBox.getChildren().add(save);
    }

    public void handleSave(){
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
            
            myDataStore.addMainCharacter(MAIN_CHARACTER_INITIAL_X_POSITION, MAIN_CHARACTER_INITIAL_Y_POSITION, MAIN_CHARACTER_WIDTH, MAIN_CHARACTER_HEIGHT, propertiesMap);
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
        TextArea text = createInputField();
        myTextInputs.add(text);
        bp.setLeft(labl);
        bp.setRight(text);
        BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
        return bp;
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
    inputField.setMinWidth(PADDED_DETAIL_WIDTH);
    inputField.setMaxWidth(PADDED_DETAIL_WIDTH);
    inputField.setMinHeight(CB_HEIGHT);
    inputField.setMaxHeight(CB_HEIGHT);
    inputField.setOnMouseClicked(e -> handleClick(inputField));
    return inputField;
}

public ComboBox<String> createComboBox(String [] boxOptions){
    ComboBox<String> cb = new ComboBox<String>();
    cb.getItems().addAll(boxOptions);
    cb.setMinWidth(CB_WIDTH);
    cb.setMaxWidth(CB_WIDTH);
    cb.setMinHeight(CB_HEIGHT);
    cb.setMaxHeight(CB_HEIGHT);
    return cb;
}

public void handleClick(TextArea field){
    field.setText("");
}

public void createTextField(){

}

}
