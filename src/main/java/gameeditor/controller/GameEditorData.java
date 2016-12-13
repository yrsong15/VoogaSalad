package gameeditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import frontend.util.GameEditorException;
import gameeditor.commanddetails.DetailDefaultsResources;
import gameeditor.commanddetails.DetailResources;
import gameeditor.commanddetails.ISelectDetail;
import gameeditor.controller.interfaces.IGameEditorData;
import gameengine.view.GameScreen;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import objects.GameObject;
import objects.Player;
import objects.ProjectileProperties;
import objects.RandomGeneration;
import objects.ScrollType;
import objects.interfaces.IGame;
import objects.interfaces.ILevel;

/**
 * @author pratikshasharma, John Martin
 */
public class GameEditorData implements IGameEditorData{
    private ArrayList<Map<String, String>> mySpriteTypes = new ArrayList<Map<String, String>>();
    private ArrayList<Map<String,String>> myImageViewObjectMap = new ArrayList<Map<String,String>>();
    private ArrayList<Map<String,String>> myMainCharImageViewMaps= new ArrayList<Map<String,String>>();
    private Map<String,ProjectileProperties> myProjectileObjects = new HashMap<String,ProjectileProperties>();
    private Map<String,Map<KeyCode,String>> myPlayerControlsMap = new HashMap<String,Map<KeyCode,String>>();
    

    private ILevel myLevel;
    private IGame myGame;


    public GameEditorData(ILevel level, IGame myGameInterface){
        myLevel = level;
        myGame = myGameInterface;
    }

    public void addControls(String typeName, Map<KeyCode,String> controlMap){
        myPlayerControlsMap.put(typeName, controlMap);
    }

    public void storeType(Map<String, String> typeMap){
        mySpriteTypes.add(typeMap);
    }

    @Override
    public void storeMainCharater (Map<String, String> myMainCharMap) {
        // TODO Auto-generated method stub
        myMainCharImageViewMaps.add(myMainCharMap);
    }

    public Map<String, String> getType(String inputTypeName){
        for (Map<String, String> type : mySpriteTypes){
            String testTypeName = type.get(DetailResources.TYPE_NAME.getResource());
            if (inputTypeName.equals(testTypeName)){
                return type;
            }
        }
        return null;
    }



    public ArrayList<String> getTypes(){
        ArrayList<String> types = new ArrayList<String>();
        for (Map<String, String> type : mySpriteTypes){
            String typeName = type.get(DetailResources.TYPE_NAME.getResource());
            types.add(typeName);
        }
        return types;
    }

    public ArrayList<String> getImageViews(){
        ArrayList<String> views = new ArrayList<String>();
        for(Map<String,String> map: myImageViewObjectMap){
            String imageview = map.get(DetailResources.IMAGEVIEW_KEY.getResource());
            views.add(imageview);
        } 
        return views;
    }


    @Override
    public Map<String, String> getSpriteViewMapByImageView (String viewName) {
        return getDesiredMap(DetailResources.IMAGEVIEW_KEY.getResource(),myImageViewObjectMap,viewName);
    } 

    private Map<String,String> getDesiredMap(String key, ArrayList<Map<String,String>> mapList, String value){
        for(Map<String,String> map: mapList){
            String valueFromMap = map.get(key);
            if (value.equals(valueFromMap)){
                return map;
            }
        }
        return null;
    }

    public Map<String,String> getMainCharMap(String imageViewName){
        return getDesiredMap(DetailResources.IMAGEVIEW_KEY.getResource(),myMainCharImageViewMaps,imageViewName);
    }

    @Override
    public Map<String,String> getSpriteViewMapByType (String typeName, String imageViewString) {
        Map<String,String> type = getType(typeName);
        Map<String,String> viewMap = new HashMap<String,String>(type);
        viewMap.put(DetailResources.IMAGEVIEW_KEY.getResource(), imageViewString);
        return viewMap;
    }

    public void storeImageViewMap(Map<String,String> viewMap){
        myImageViewObjectMap.add(viewMap);
    }

    public void addProjectileProperties(String typeName, ProjectileProperties properties){
        myProjectileObjects.put(typeName, properties);
    }
    
    public void addRandomGeneration(String type, List<TextArea> myRandomGenerationParameters, ComboBox<String> isEnemyAllowed){
        Map<String,String> properties=  getType(type);
        String isenemyallowed = isEnemyAllowed.getValue();
       
        Map<String,String> propertiesMap = getPropertiesMap(properties);
        
        propertiesMap.put("isenemyallowed", "true");
        //addRandomGeneration(propertiesMap, myRandomGenerationParameters);
        //mySpriteTypes.remove(properties);
  
       
            double width = Double.valueOf(properties.get(WIDTH_KEY));
            double height = Double.valueOf(properties.get(HEIGHT_KEY));
            String imagePath = properties.get(IMAGE_PATH_KEY);
            
            String file = imagePath.substring(imagePath.lastIndexOf("/") +1);
            Integer num = Integer.parseInt(myRandomGenerationParameters.get(0).getText());
            if(num==0){num=5;}
            Integer xMin = Integer.parseInt(myRandomGenerationParameters.get(1).getText());
            if(xMin==0){xMin=(int) (GameScreen.screenWidth/5);}
            Integer xMax = Integer.parseInt(myRandomGenerationParameters.get(2).getText());
            if(xMax==0){xMax=(int) GameScreen.screenWidth;}
            Integer yMin = Integer.parseInt(myRandomGenerationParameters.get(3).getText());
            if(yMin==0){yMin=((int) (GameScreen.screenHeight*0.2));}
            Integer yMax = Integer.parseInt(myRandomGenerationParameters.get(4).getText());
            if(yMax==0){yMax=(int) (GameScreen.screenHeight*0.6);}
            Integer minSpacing = Integer.parseInt(myRandomGenerationParameters.get(5).getText());
            if(minSpacing==0){minSpacing=250;}
            Integer maxSpacing = Integer.parseInt(myRandomGenerationParameters.get(6).getText());
            if(maxSpacing==0){maxSpacing=500;}

            // Need width and height of the game objects
            // Image URL (bird.png)
            // 

            //RandomGeneration randomGeneration = new RandomGeneration(properties,num,xMin,xMax,yMin,yMax,minSpacing,maxSpacing);

            //myLevel.addRandomGeneration(randomGeneration);
        //}
    }


   
    


    private Map<String,String> getPropertiesMap(Map<String,String> myItemMap){
        removeValuesExceptProperties(myItemMap);
        Map<String,String> properties = new HashMap<String,String>();
        myItemMap.forEach((k,v)-> {
            if(!(v.isEmpty()) && !(v.equals(DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource())))
                properties.put(k, v);
        });
        return properties;
    }

    @Override
    public ArrayList<Map<String, String>> getTypeMaps() {
        return mySpriteTypes;
    }

    public void addWinCondition(String type, String action){
        myLevel.addWinCondition(type, action);
    }

    public void addLoseCondition(String type, String action){
        myLevel.addLoseCondition(type, action);
    }

    public void addGameObjectsToLevel(){   
        for (Map<String, String> type : myImageViewObjectMap){
            GameObject myObject = createGameObject(type);
            myLevel.addGameObject(myObject);   
        }
    }

    private GameObject createGameObject(Map<String,String> map){
        GameObject object=null;
        try{
            double xPosition = Double.valueOf(map.get(ISelectDetail.X_POSITION_KEY));
            double yPosition = Double.valueOf(map.get(ISelectDetail.Y_POSITION_KEY));
            double width = Double.valueOf(map.get(WIDTH_KEY));
            double height = Double.valueOf(map.get(HEIGHT_KEY));

            String imagePath = map.get(IMAGE_PATH_KEY);
            String file = imagePath.substring(imagePath.lastIndexOf("/") +1);
            Map<String,String> properties = getPropertiesMap(map);
            object = new GameObject(xPosition,yPosition,width,height,file,properties); 
            object.setTypeName(map.get(DetailResources.TYPE_NAME.getResource()));


            if(!myProjectileObjects.isEmpty()&& myProjectileObjects.containsKey(map.get(DetailResources.TYPE_NAME.getResource()))){
                ProjectileProperties projectileProp= myProjectileObjects.get(map.get(object.getTypeName()));
                object.setProjectileProperties(projectileProp);
            }
            return object;
        }catch(NullPointerException e){
            GameEditorException exception = new GameEditorException();
            exception.showError(e.getMessage());
        }
        return object;
    }

    private void removeValuesExceptProperties(Map<String,String> typeMap){
        typeMap.remove(IMAGE_PATH_KEY);
        typeMap.remove(HEIGHT_KEY);
        typeMap.remove(WIDTH_KEY);
        typeMap.remove(ISelectDetail.X_POSITION_KEY);
        typeMap.remove(ISelectDetail.Y_POSITION_KEY);
        if(typeMap.containsKey(DetailResources.IMAGEVIEW_KEY.getResource())){
            typeMap.remove(DetailResources.IMAGEVIEW_KEY.getResource());
        }
    }


    @Override
    public void storeMainCharToXML () {
        // TODO: Add Objects
        for(Map<String,String> map: myMainCharImageViewMaps){
            GameObject myObject = createGameObject(map);
            Player player = new Player(myObject);
            //if(myPlayerControlsMap.containsKey(myObject.getTypeName())){
            player.setControlMap(myPlayerControlsMap.get(myObject.getTypeName()));
            //}else {
            //GameEditorException exception = new GameEditorException();
            //exception.showError("Not all Players have Controls Set up");
            // }
            myGame.addPlayer(player);
            myLevel.addGameObject(myObject);  
            //});
        }

    }

    @Override
    public ArrayList<String> getMainCharacterTypes () {
        ArrayList<String> types = new ArrayList<String>();
        for (Map<String, String> type : myMainCharImageViewMaps){
            String typeName = type.get(DetailResources.TYPE_NAME.getResource());
            types.add(typeName);
        }
        return types;
    }

    public void removeGameobjectView (String imageViewName) {
        removeFromMapList(myImageViewObjectMap,imageViewName);
        removeFromMapList(myMainCharImageViewMaps,imageViewName);
    }

    private void removeFromMapList(ArrayList<Map<String,String>> mapList, String imageViewName ){
        for(Map<String,String> map: mapList){
            if(map.containsValue(imageViewName)){
                mapList.remove(map);
            }
        }

    }
}


