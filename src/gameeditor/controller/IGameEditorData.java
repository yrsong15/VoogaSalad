package gameeditor.controller;

import java.util.ArrayList;
import java.util.Map;
/**
 *  To be used by the front end components to send data to the back end controller
 * @author pratikshasharma
 *
 */
public interface IGameEditorData {
    public Map<String, String> getType(String inputTypeName);
    public void storeType(Map<String, String> typeMap);
    public ArrayList<String> getTypes();
    
    public void addControls(String key, String value);
    public void addGameObject(double xPos, double yPos, double width, double height, String imageFileName,
                              Map<String, String> properties);
    
    
    
}
