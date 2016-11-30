package gameeditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import gameeditor.commanddetails.CreateObjectDetail;
import gameeditor.commanddetails.DetailResources;
import gameeditor.controller.interfaces.IGameEditorData;
import gameengine.view.GameEngineUI;
import javafx.scene.input.KeyCode;
import objects.GameObject;
import objects.RandomGeneration;
import objects.interfaces.ILevel;

/**
 * @author pratikshasharma, John Martin
 */

public class GameEditorData implements IGameEditorData{
    private ArrayList<Map<String, String>> myTypes = new ArrayList<Map<String, String>>();

    private ILevel myLevel;

    public static final double SPRITE_WIDTH = 50;
    public static final double SPRITE_HEIGHT = 50;
    private String mainCharacterImageFilePath;

    public GameEditorData(ILevel level){
        myLevel = level;
    }


    public void storeType(Map<String, String> typeMap){
        myTypes.add(typeMap);
    }

    public Map<String, String> getType(String inputTypeName){
        for (Map<String, String> type : myTypes){
            String testTypeName = type.get(DetailResources.TYPE_NAME.getResource());
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



    // Adds Game Object TO level 
    //TODO: Rmeove hardcoding of String values
    public void addGameObjectToLevel(Map<String,String> myGameObjMap){       
        Map<String,String> properties = getPropertiesMap(myGameObjMap);
        double xpos =  Double.parseDouble(myGameObjMap.get(CreateObjectDetail.X_POSITON_KEY));
        double ypos =  Double.parseDouble(myGameObjMap.get(CreateObjectDetail.Y_POSITION_KEY));
        String imagePath = myGameObjMap.get("Image Path");
        
        String file = imagePath.substring(imagePath.lastIndexOf("/") +1);
        
        GameObject myObject = new GameObject(xpos,ypos,SPRITE_WIDTH,SPRITE_HEIGHT,file,properties);
        
        RandomGeneration randomGeneration = new RandomGeneration(myObject.getProperties(), 5, (int) GameEngineUI.myAppWidth / 5, (int) GameEngineUI.myAppWidth,
                                                                 -100, (int) GameEngineUI.myAppHeight - 300, 250, 500);
                                                         
        myLevel.addRandomGeneration(randomGeneration);                                           
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


    public void addControl(KeyCode key, String action){
        myLevel.addControl(key, action);
    }


    @Override
    public ArrayList<Map<String, String>> getTypeMaps() {
        return myTypes;
    }
  
    public void addWinCondition(String type, String value){
        myLevel.addWinCondition(type, value);
    }

    @Override
    public void addMainCharacterImage (String imageFilePath) {
        this.mainCharacterImageFilePath = imageFilePath;
    }
    
    public void addMainCharacter(double xpos, double ypos, double width, double height, Map<String,String> properties){
        GameObject mainCharacter = new GameObject(xpos,ypos,SPRITE_WIDTH,SPRITE_HEIGHT,this.mainCharacterImageFilePath,properties);
        myLevel.addGameObject(mainCharacter);
        myLevel.setMainCharacter(mainCharacter);
    }
}
