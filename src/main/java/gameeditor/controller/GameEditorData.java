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
import gameengine.model.RandomGenFrame;
import gameengine.model.RandomGenFrameX;
import gameengine.model.RandomGenFrameY;
import gameengine.view.GameScreen;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
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
    private ArrayList<Map<String, String>> mySpriteTypes ;
    private ArrayList<Map<String,String>> myImageViewObjectMap ;
    private ArrayList<Map<String,String>> myMainCharImageViewMaps;
    private Map<String,ProjectileProperties> myProjectileObjects ;
    private Map<String,Map<KeyCode,String>> myPlayerControlsMap;
    @SuppressWarnings("rawtypes")
    private Map<String,RandomGeneration> myTypeRandomGenerationMap;
    private List<String> spriteToRemove ;

    private ILevel myLevel;
    private IGame myGame;

    private boolean enemyAllowed;
    private String randomGenDirection;



    @SuppressWarnings("rawtypes")
    public GameEditorData(ILevel level, IGame myGameInterface){
        myLevel = level;
        myGame = myGameInterface;
        myTypeRandomGenerationMap = new HashMap<String,RandomGeneration>();
        myPlayerControlsMap = new HashMap<String,Map<KeyCode,String>>();
        myProjectileObjects = new HashMap<String,ProjectileProperties>();
        myMainCharImageViewMaps= new ArrayList<Map<String,String>>();
        myImageViewObjectMap = new ArrayList<Map<String,String>>();
        mySpriteTypes = new ArrayList<Map<String, String>>();
        spriteToRemove = new ArrayList<String>();
    }

    public void addControls(String typeName, Map<KeyCode,String> controlMap){
         typeName = typeName.replaceAll("\\s+","");
        myPlayerControlsMap.put(typeName, controlMap);
    }

    public void storeType(Map<String, String> typeMap){
        mySpriteTypes.add(typeMap);
    }

    @Override
    public void storeMainCharater (Map<String, String> myMainCharMap) {
        String typeName = myMainCharMap.get(DetailResources.TYPE_NAME.getResource());
        String typeKey = typeName.replaceAll("\\s+","");
        myMainCharMap.put(DetailResources.TYPE_NAME.getResource(), typeKey);
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
        String typeKey = typeName.replaceAll("\\s+","");
        myProjectileObjects.put(typeKey, properties);
    }

    public void addRandomGeneration(String type, List<TextArea> myRandomGenerationParameters, 
                                    ComboBox<String> isEnemyAllowed, ComboBox<String> direction){
        Map<String,String> properties =  getType(type);
        enemyAllowed = Boolean.getBoolean(isEnemyAllowed.getValue());
        randomGenDirection = direction.getValue();


        int width = Double.valueOf(properties.get(WIDTH_KEY)).intValue();
        int height = Double.valueOf(properties.get(HEIGHT_KEY)).intValue();
        String imagePath = properties.get(IMAGE_PATH_KEY);
        String file = imagePath.substring(imagePath.lastIndexOf("/") +1);
        
        Map<String,String> propertiesMap = getPropertiesMap(properties);
        propertiesMap.put("isenemyallowed", "true");

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

        @SuppressWarnings({ "unchecked", "rawtypes" })
        RandomGeneration randomGeneration = new RandomGeneration((HashMap) properties,width,height,file,num,xMin,xMax,yMin,yMax,minSpacing,maxSpacing);
        myTypeRandomGenerationMap.put(DetailResources.TYPE_NAME.getResource(), randomGeneration);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addRandomGenerationFrame(){
        if(!myTypeRandomGenerationMap.isEmpty()){
            ArrayList<RandomGeneration> list = new ArrayList<RandomGeneration>();
            myTypeRandomGenerationMap.forEach((k,v)->{
                list.add(v);
            });

            System.out.println(list.size());
            
            RandomGenFrame frame=null;
            if(randomGenDirection.equals("vertical")){
                frame = new RandomGenFrameY(myGame.getCurrentLevel(), list, enemyAllowed);
            }else if (randomGenDirection.equals("horizontal")){
                frame = new RandomGenFrameX(myGame.getCurrentLevel(), list, enemyAllowed);
            }

            System.out.println(frame);
            myLevel.setRandomGenerationFrame(frame);
        }
    }



    private Map<String,String> getPropertiesMap(Map<String,String> myItemMap){
        removeValuesExceptProperties(myItemMap);
        Map<String,String> properties = new HashMap<String,String>();
        myItemMap.forEach((k,v)-> {
            if(v!=null){
            if(!(v.isEmpty()) && !(v.equals(DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource())))
                properties.put(k, v);
            }
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
        removeFromMapList(myImageViewObjectMap);
        for (Map<String, String> type : myImageViewObjectMap){
            if(!myTypeRandomGenerationMap.containsKey(type.get(DetailResources.TYPE_NAME.getResource()))){
                GameObject myObject = createGameObject(type);
                myLevel.addGameObject(myObject);   
            }
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
            
            // Remove Spaces from TypeName
            String typeName = map.get(DetailResources.TYPE_NAME.getResource());
            String typeKey = typeName.replaceAll("\\s+","");
           
            map.put(DetailResources.TYPE_NAME.getResource(), typeKey);
         
            object.setTypeName(map.get(DetailResources.TYPE_NAME.getResource()));
            
            
            if(!myProjectileObjects.isEmpty()&& myProjectileObjects.containsKey(map.get(DetailResources.TYPE_NAME.getResource()))){             
                ProjectileProperties projectileProp= myProjectileObjects.get(map.get((DetailResources.TYPE_NAME.getResource())));         
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
        removeFromMapList(myMainCharImageViewMaps);
        
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
            myLevel.addPlayer(player.getMainChar());
            
//            if(!myProjectileObjects.isEmpty()&& myProjectileObjects.containsKey(map.get(DetailResources.TYPE_NAME.getResource()))){
//                ProjectileProperties projectileProp= myProjectileObjects.get(map.get(myObject.getTypeName()));
//                myObject.setProjectileProperties(projectileProp);
//            }
//            
           //myLevel.addGameObject(myObject);  
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
        spriteToRemove.add(imageViewName);
       
    }

    private void removeFromMapList(ArrayList<Map<String,String>> mapList ){
        for(Map<String,String> map: mapList){
            for(String imageview: spriteToRemove){
                if(map.containsValue(imageview)){
                    mapList.remove(map);
                }
            }
        }
    }
    
    public void addScrollType(ScrollType scrolltype){
        myLevel.setScrollType(scrolltype);
    }
}


