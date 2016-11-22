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
    private Map<String, Integer> gameConditions;
    private List<RandomGeneration> randomGenerations;
    private GameObject mainCharacter;
    private ScrollType scrollType;
    private Settings levelSettings;

    public Level(int level) {
        gameObjects = new ArrayList<GameObject>();
        randomGenerations = new ArrayList<RandomGeneration>();
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
    	return gameConditions.get("score");
    }

    public void setScore(int score){
        gameConditions.put("score", score);
    }

    public int getTime(){
    	return gameConditions.get("time");
    }

    public void setTime(int time){
    	gameConditions.put("time", time);
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