package gameeditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import gameeditor.commanddetails.DetailResources;
import gameeditor.controller.interfaces.ILevelSettings;
import objects.GameObject;

/**
 * @author pratikshasharma
 */

public class GameEditorData implements IGameEditorData, ILevelSettings{

    private GameEditorBackendController myGameEditorBackEndController;
    private HashMap<String,String> myControlMap;
    private ArrayList<Map<String, String>> myTypes = new ArrayList<Map<String, String>>();

    public GameEditorData(){
        myGameEditorBackEndController = new GameEditorBackendController();
        myControlMap = new HashMap<String,String>();
    }


    public void storeType(Map<String, String> typeMap){
        myTypes.add(typeMap);
    }

    public Map<String, String> getType(String inputTypeName){
        for (Map<String, String> type : myTypes){
            String testTypeName = type.get(DetailResources.TYPE_NAME.getResource());

            // Type Name is the String for the part
            // 
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

    public void addControls(String key, String value){
        myControlMap.put(key, value);
    }

    public void addGameObject(double xPos, double yPos, double width, double height, String imageFileName,
                              Map<String, String> properties){

        GameObject myGameObject = new GameObject( xPos, yPos, width,  height, imageFileName,
                                                  properties);

        //Call in the AddCurrentGameObject inside the BE controller
        //myGameEditorBackEndController.addCurrentGameObjectToLevel(myGameObject);
    }

    
    public void addGameObjectXYImage(double xposition, double yposition, String imageFilePath, String TypeName){

    }

    @Override
    public void setBackgroundImage (String filePath) {
        myGameEditorBackEndController.addBackgroundImage(filePath);
    }

    @Override
    public void setMusic (String filePath) {
       myGameEditorBackEndController.addBackgroundMusic(filePath);
        
    }

    @Override
    public void setMainCharacterImage (String filePath) {
         
    }

	@Override
	public ArrayList<Map<String, String>> getTypeMaps() {
		return myTypes;
	}
}
