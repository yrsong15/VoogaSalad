package gameeditor.controller.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import objects.ProjectileProperties;
import objects.ScrollType;
/**
 *  To be used by the front end components to send data to the back end controller
 * @author pratikshasharma, John Martin
 *
 */
public interface IGameEditorData {

    public static final String IMAGE_PATH_KEY="Image Path";
    public static final String WIDTH_KEY="width";
    public static final String HEIGHT_KEY="height";
    public static final double MAIN_CHAR_WIDTH=50;
    public static final double MAIN_CHAR_HEIGHT = 50;

    // Types Methods
    public Map<String, String> getType(String inputTypeName);
    public void storeType(Map<String, String> typeMap);
    public ArrayList<String> getTypes();	
    public ArrayList<Map<String, String>> getTypeMaps();

    public void addLoseCondition(String type, String action);
    public void addWinCondition(String type, String action);
   

    public void addGameObjectsToLevel();

    public void addRandomGeneration(String type, List<TextArea> myRandomGenerationParameters,
                                    ComboBox<String> isEnemyAllowed,ComboBox<String> direction);


    public ArrayList<String> getImageViews();
    
    public Map<String, String> getSpriteViewMapByImageView (String viewName) ;
    
    public Map<String,String> getSpriteViewMapByType (String typeName, String imageViewString) ;
    public void storeImageViewMap(Map<String,String> viewMap);
    
    public void storeMainCharater(Map<String,String> myMainCharMap);
    public Map<String,String> getMainCharMap(String imageViewName);
    public void storeMainCharToXML();
    
    public ArrayList<String> getMainCharacterTypes();
    public void addProjectileProperties(String typeName, ProjectileProperties properties);
    
    public void addControls(String typeName, Map<KeyCode,String> controlMap);
    
    public void removeGameobjectView(String imageViewName);
    
    public void addRandomGenerationFrame();
    
    public void addScrollType(ScrollType scrolltype);
    
    public Map<KeyCode,String> getControlsMap(String typeName);
}

