package gameeditor.commanddetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import com.sun.javafx.scene.traversal.Direction;
import frontend.util.GameEditorException;
import gameeditor.controller.interfaces.IGameEditorData;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import objects.ProjectileProperties;

//This entire file is part of my masterpiece.
//Pratiksha Sharma
// This class shows the implementation of data driven design through the use of ResourceBundle and enums.It shows 
// use of exceptions in handling errors. 
/**
 * @author Pratiksha Sharma
 *
 */
public class ProjectileDetail implements IDetailTab{
    private IGameEditorData myDataStore;
    private VBox myVBox;
    private DetailFrontEndUtil myDetailFrontEndUtil;
    private ImageDetail myImageDetail;
    private List<TextArea> myTextInputs;
    private ComboBox<String> projectileDirection;
    private ComboBox<String> myTypes;
    private String myImageFile;
    private ResourceBundle myResources;

    public static final String ERROR_FILE=DetailResources.ERROR_FILE.getResource();
    public static final String EMPTY_IMAGE_ERROR_PROPERTY=DetailResources.EMPTY_IMAGE_ERROR_PROPERTY.getResource();
    public static final String INVALD_NUMBER_INPUT_ERROR=DetailResources.INVALD_NUMBER_INPUT_ERROR.getResource();
    public static final String SELECT_LABEL=DetailResources.SELECT_LABEL.getResource();
    public static final String SAVE_BUTTON_PROPERTY=DetailResources.SAVE_BUTTON_PROPERTY.getResource();


    public ProjectileDetail(IGameEditorData dataStore){
        myDataStore = dataStore;
        myImageDetail= new ImageDetail();
        myDetailFrontEndUtil = new DetailFrontEndUtil();
        myTextInputs = new ArrayList<TextArea>();
        myResources = ResourceBundle.getBundle(ERROR_FILE, Locale.getDefault());
    }

    /**
     * @param returns the VBox that contains the content
     */
    public VBox getTabContent(){
        myVBox = new VBox();
        myVBox.setSpacing(IAbstractCommandDetail.MY_DETAIL_PADDING);
        myVBox.setAlignment(Pos.BASELINE_CENTER);
        createSpriteTypesCombo();
        addProperties();
        return myVBox;
    }

    private void addProperties(){
        addDirection();
        addTextAreaProperties();
        addImagePane();
        createSave(e-> handleSave());
    }

    private void createSave(EventHandler<MouseEvent> handler){
        Button save = myDetailFrontEndUtil.createButton(SAVE_BUTTON_PROPERTY,handler);
        myVBox.getChildren().add(save);
    }

    private void handleSave(){
        Direction direction=null;
        if(projectileDirection.getValue()!=null){
            direction= Direction.valueOf(projectileDirection.getValue());
        }
        myImageFile = myImageDetail.getFilePath();
        myImageFile = myImageFile.substring(myImageFile.lastIndexOf("/") +1);

        if(myImageFile.isEmpty()){
            GameEditorException ex = new GameEditorException();
            ex.showError(myResources.getString(EMPTY_IMAGE_ERROR_PROPERTY));
        } 

        try{
            double width = Double.valueOf(getText(myTextInputs.get(0)));
            double height = Double.valueOf(getText(myTextInputs.get(1)));
            double range = Double.valueOf(getText(myTextInputs.get(2)));
            double speed = Double.valueOf(getText(myTextInputs.get(3)));
            double damage = Double.valueOf(getText(myTextInputs.get(4)));
            double timeBetweenShots = Double.valueOf(getText(myTextInputs.get(4)));
            String type = myTypes.getValue();
            
            ProjectileProperties property = new ProjectileProperties(myImageFile,width,height,direction,range,speed,damage,timeBetweenShots);
            myDataStore.addProjectileProperties(type, property);

        }catch(RuntimeException e){
            GameEditorException ex = new GameEditorException();
            ex.showError(myResources.getString(INVALD_NUMBER_INPUT_ERROR));
        }
    }

    private String getText( TextArea area){
        String value = area.getText();
        if(value.isEmpty()|| value==null ){
            value = DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource();
        }
        return value;
    }

    private void addImagePane(){
        myVBox.getChildren().add(myImageDetail.createImageChoose());
    }

    private void addTextAreaProperties(){
        String [] propertiesList = DetailResources.PROJECTILE_TEXT_INPUT_PROPERTIES_LABEL.getArrayResource();
        for(String property: propertiesList){
            Label label = myDetailFrontEndUtil.createPropertyLbl(property);
            String init = DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource();
            TextArea myTextArea= myDetailFrontEndUtil.createInputField(init);
            BorderPane bp = myDetailFrontEndUtil.createBorderpane(myTextArea, label);
            myVBox.getChildren().add(bp);
            myTextInputs.add(myTextArea);
        }
    }

    private void createSpriteTypesCombo(){
        ArrayList<String>  listOfTypes = myDataStore.getTypes();
        listOfTypes.addAll(myDataStore.getMainCharacterTypes());     
        String[] types = listOfTypes.toArray(new String[listOfTypes.size()]);
        Label labl = myDetailFrontEndUtil.createPropertyLbl(SELECT_LABEL);
        myTypes = myDetailFrontEndUtil.createComboBox(types, null);
        myTypes.setMaxWidth(IAbstractCommandDetail.PADDED_DETAIL_WIDTH*1.5);
        myTypes.setMinWidth(IAbstractCommandDetail.PADDED_DETAIL_WIDTH*1.5);
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(myTypes, labl);
        myVBox.getChildren().add(bp);
    }

    private void addDirection(){
        Label label = myDetailFrontEndUtil.createPropertyLbl(DetailResources.DIRECTION_LABEL.getResource());
        String defaultDirection = DetailDefaultsResources.PROJECTILE_DIRECTION.getResource();
        projectileDirection = myDetailFrontEndUtil.createComboBox(DetailResources.SCROLL_DIRECTIONS_OPTIONS.getArrayResource(), defaultDirection);
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(projectileDirection, label);
        myVBox.getChildren().add(bp);
    }
}