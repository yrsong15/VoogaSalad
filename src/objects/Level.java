package objects;

import java.util.*;

/**
 * Created by Soravit on 11/18/2016.
 */
public class Level {

    private int level;
    private List<GameObject> gameObjects;
    private Map<String, String> winConditions;
    private Map<String, String> loseConditions;
    private GameObject mainCharacter;
    private ScrollType scrollType;
    private Settings levelSettings;
    private int score;
    private int time;

    public Level(int level) {
        gameObjects = new ArrayList<GameObject>();
        winConditions = new HashMap<String, String>();
        loseConditions = new HashMap<String, String>();
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

    public void addWinCondition(String type, String action) {
        winConditions.put(type, action);
    }

    public void removeWinCondition(String type, String action){
        winConditions.remove(type);
    }

    public void addLoseCondition(String type, String action) {
        loseConditions.put(type, action);
    }

    public void removeLoseCondition(String type, String action){
        loseConditions.remove(type);
    }

    public GameObject getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(GameObject mainCharacter) {
        this.mainCharacter = mainCharacter;
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
    
    public Settings getLevelSettings(){
        return levelSettings;
    }
    
    public void setLevelSettings(Settings levelSettings){
        this.levelSettings = levelSettings;
    }
}