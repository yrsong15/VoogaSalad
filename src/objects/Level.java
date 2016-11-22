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
    private Map<String, Double> gameConditions;
    private List<RandomGeneration> randomGenerations;
    private GameObject mainCharacter;
    private ScrollType scrollType;
    private Settings viewSettings;

    public Level(int level) {
        gameObjects = new ArrayList<GameObject>();
        randomGenerations = new ArrayList<RandomGeneration>();
        winConditions = new HashMap<>();
        loseConditions = new HashMap<>();
        gameConditions = new HashMap<>();
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

    //TODO: getScore returns int, but Score is stored as double for functionality purposes 
    //let me know if you think this is wrong!! - Ray Song
    public int getScore(){
    	return gameConditions.get("score").intValue();
    }

    public void setScore(double score){
        gameConditions.put("score", score);
    }

    public double getTime(){
    	return gameConditions.get("time");
    }

    public void setTime(double time){
    	gameConditions.put("time", time);
    }
    
    public List<GameObject> getGameObjects(){
    	return gameObjects;
    }
    
    public Settings getViewSettings(){
        return viewSettings;
    }
    
    public void setViewSettings(Settings viewSettings){
        this.viewSettings = viewSettings;
    }
}