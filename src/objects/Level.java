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
    private ScrollType scrollType;
    private int score;
    private int time;

    public Level(int level) {
        gameObjects = new ArrayList<GameObject>();
        externalRules = new HashMap<String, String>();
        this.level = level;
    }

    public void setScrollType(ScrollType scrollType){
        this.scrollType = scrollType;
    }

    public ScrollType scrollType(){
        return scrollType;
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

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }
    
    public List<GameObject> getGameObjects(){
    	return gameObjects;
    }
}
