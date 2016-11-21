package objects;

import java.util.*;

/**
 * Created by Soravit on 11/18/2016.
 */
public class Level {

    private int level;
    private List<GameObject> gameObjects;
    private Map<String, String> externalRules;
    private GameObject mainCharacter;
    private Settings myLevelSettings;

    public Level(int level) {
        gameObjects = new ArrayList<GameObject>();
        externalRules = new HashMap<String, String>();
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public void removeGameObject(GameObject go) {
        gameObjects.remove(go);
    }

    public void addRule(String type, String action) {
        externalRules.put(type, action);
    }

    public void removeRule(String type) {
        externalRules.remove(type);
    }

    public GameObject getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(GameObject character) {
        this.mainCharacter = mainCharacter;
    }

    public Set<String> getExternalRules(){
        return externalRules.keySet();
    }
    
    public List<GameObject> getGameObjects(){
    	return gameObjects;
    }
    
    public Settings getLevelSettings(){
        return myLevelSettings;
    }
    
    public void setLevelSettings(Settings settings){
        this.myLevelSettings= settings;
    }
}
