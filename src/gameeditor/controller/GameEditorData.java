package gameeditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameeditor.commanddetails.CreateObjectDetail;
import gameeditor.commanddetails.DetailResources;
import gameeditor.controller.interfaces.IGameEditorData;
import javafx.scene.input.KeyCode;
import objects.GameObject;
import objects.interfaces.ILevel;

/**
 * @author pratikshasharma, John Martin
 */

public class GameEditorData implements IGameEditorData{

    private GameEditorBackendController myGameEditorBackEndController;
    private HashMap<String,String> myControlMap;
    private ArrayList<Map<String, String>> myTypes = new ArrayList<Map<String, String>>();

    private ILevel myLevel;
    private List<Map<String,String>> myGameObjects;

    public static final double SPRITE_WIDTH = 50;
    public static final double SPRITE_HEIGHT = 50;

    public GameEditorData(ILevel level){
        myLevel = level;
        myGameObjects = new ArrayList<Map<String,String>>();
    }


    public void storeType(Map<String, String> typeMap){
        myTypes.add(typeMap);
    }

    public Map<String, String> getType(String inputTypeName){
        for (Map<String, String> type : myTypes){
            String testTypeName = type.get(DetailResources.TYPE_NAME.getResource());

            // Type Name is the String for the part
            if (inputTypeName.equals(testTypeName)){
                return type;
            }
        }
        return null;
    }

    public ArrayList<String> getTypes(){
        ArrayList<String> types = new ArrayList<String>();
        for (Map<String, String> type : myTypes){
            String typeName = type.get(DetailResources.TYPE_NAME.getResource());
            types.add(typeName);
        }
        return types;
    }

    public void addControl(KeyCode key, String action){
        myLevel.addControl(key, action);
    }

    // Adds Game Object TO level 
    //TODO: Rmeove hardcoding of String values
    public void addGameObjectToLevel(Map<String,String> myGameObjMap){       
        Map<String,String> properties = getPropertiesMap(myGameObjMap);
        double xpos =  Double.parseDouble(myGameObjMap.get(CreateObjectDetail.X_POSITON_KEY));
        double ypos =  Double.parseDouble(myGameObjMap.get(CreateObjectDetail.Y_POSITION_KEY));
        String imagePath = myGameObjMap.get("Image Path");
        GameObject myObject = new GameObject(xpos,ypos,SPRITE_WIDTH,SPRITE_HEIGHT,imagePath,properties);
        myLevel.addGameObject(myObject);
    }

    private Map<String,String> getPropertiesMap(Map<String,String> myItemMap){

        // include everything except for xposition, yposition, imagefilePath, Type Name in the properties Map
        Map<String,String> properties = new HashMap<String,String>();
        properties.put("damage",myItemMap.get("damage"));
        myItemMap.forEach((k,v)-> {
            if(!(k.equals("xPosition")|| k.equals("yPosition")|| k.equals("Type Name") || k.equals("Image Path" ))){
                properties.put(k, v);
            }
        });
        return properties;
    }

    public void addControlsMap(){

    }

//    @Override
//    public void setMainCharacterImage (String filePath) {
//
//    }

    @Override
    public ArrayList<Map<String, String>> getTypeMaps() {
        return myTypes;
    }


//    @Override
//    public void setBackgroundImage(String filePath) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//    @Override
//    public void setMusic(String filePath) {
//        // TODO Auto-generated method stub
//
//    }
}
