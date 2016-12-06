package gameeditor.controller.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import objects.ScrollType;
/**
 *  To be used by the front end components to send data to the back end controller
 * @author pratikshasharma
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

    // Controls Methods
    public void addControl(KeyCode key, String value);

    // Add Game Object to Level
    public void addGameObjectToLevel(Map<String,String> myGameObjects);

    public void addMainCharacterImage(String imageFilePath);
    public void addMainCharacter(double xpos, double ypos, double width, double height, Map<String,String> properties);
    
    public void addScrollType(ScrollType scrollType);
    public void addLoseCondition(String type, String action);
    public void addWinCondition(String type, String action);
    public void addScrollWidth(String width);
    
    public void addScrollSpeed(String speed);
    
    public void addGameObjectsToLevel();
    
    public void addRandomGeneration(String type, List<TextArea> myRandomGenerationParameters);
    
}
