package gameeditor.controller.interfaces;

import java.util.ArrayList;
import java.util.Map;
import javafx.scene.input.KeyCode;
/**
 *  To be used by the front end components to send data to the back end controller
 * @author pratikshasharma
 *
 */
public interface IGameEditorData {

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
}
