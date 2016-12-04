package gameeditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameeditor.commanddetails.CreateObjectDetail;
import gameeditor.commanddetails.DetailResources;
import gameeditor.controller.interfaces.IGameEditorData;
import gameengine.view.GameEngineUI;
import gameengine.view.GameScreen;
import javafx.scene.control.TextArea;
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

    // public static final double SPRITE_WIDTH = 100;
    // public static final double SPRITE_HEIGHT = 150;
    public static final double MAIN_CHAR_WIDTH=50;
    public static final double MAIN_CHAR_HEIGHT = 50;
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

    public void addGameObjectToLevel(Map<String,String> myGameObjMap, List<TextArea> myRandomGenerationParameters){
        double xpos =  Double.parseDouble(myGameObjMap.get(CreateObjectDetail.X_POSITION_KEY));
        double ypos =  Double.parseDouble(myGameObjMap.get(CreateObjectDetail.Y_POSITION_KEY));
        double width = Double.parseDouble(myGameObjMap.get(CreateObjectDetail.SPRITE_WIDTH_KEY));
        double height = Double.parseDouble(myGameObjMap.get(CreateObjectDetail.SPRITE_HEIGHT_KEY));

        // remove position values and size values from map
        myGameObjMap.remove(CreateObjectDetail.X_POSITION_KEY);
        myGameObjMap.remove(CreateObjectDetail.Y_POSITION_KEY);
        myGameObjMap.remove(CreateObjectDetail.SPRITE_WIDTH_KEY);
        myGameObjMap.remove(CreateObjectDetail.SPRITE_HEIGHT_KEY);

        Map<String,String> properties = getPropertiesMap(myGameObjMap);


        String imagePath = myGameObjMap.get("Image Path");

        String file = imagePath.substring(imagePath.lastIndexOf("/") +1);

        GameObject myObject = new GameObject(xpos,ypos,width,height,file,properties);

        // Add random Generation
        if(myRandomGenerationParameters.size()>0){
            addRandomGeneration(myObject.getProperties(), myRandomGenerationParameters);
        }else {
            myLevel.addGameObject(myObject);
        }

    }

    private void addRandomGeneration(Map<String,String> properties, List<TextArea>myRandomGenParameters){
        Integer num = Integer.parseInt(myRandomGenParameters.get(0).getText());
        if(num==0){num=5;}
        Integer xMin = Integer.parseInt(myRandomGenParameters.get(1).getText());
        if(xMin==0){xMin=(int) (GameScreen.screenWidth/5);}
        Integer xMax = Integer.parseInt(myRandomGenParameters.get(2).getText());
        if(xMax==0){xMax=(int) GameScreen.screenWidth;}
        Integer yMin = Integer.parseInt(myRandomGenParameters.get(3).getText());
        if(yMin==0){yMin=((int) (GameScreen.screenHeight*0.2));}
        Integer yMax = Integer.parseInt(myRandomGenParameters.get(4).getText());
        if(yMax==0){yMax=(int) (GameScreen.screenHeight*0.6);}
        Integer minSpacing = Integer.parseInt(myRandomGenParameters.get(5).getText());
        if(minSpacing==0){minSpacing=250;}
        Integer maxSpacing = Integer.parseInt(myRandomGenParameters.get(6).getText());

        if(maxSpacing==0){maxSpacing=500;}

        RandomGeneration randomGeneration = new RandomGeneration(properties,num,xMin,xMax,yMin,yMax,minSpacing,maxSpacing);

        myLevel.addRandomGeneration(randomGeneration);
    }

    private Map<String,String> getPropertiesMap(Map<String,String> myItemMap){
        Map<String,String> properties = new HashMap<String,String>();
        myItemMap.forEach((k,v)-> {
            properties.put(k, v);
        });
        return properties;
    }


    public void addControl(KeyCode key, String action){
        myLevel.setControl(key, action);
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
        GameObject mainCharacter = new GameObject(xpos,ypos,MAIN_CHAR_WIDTH,MAIN_CHAR_HEIGHT,this.mainCharacterImageFilePath,properties);
        myLevel.addGameObject(mainCharacter);
        myLevel.setMainCharacter(mainCharacter);
    }
}



