package objects;

import java.util.*;


/**
 * Created by Soravit on 11/18/2016.
 */
public class Level {

    private int level;
    private List<GameObject> gameObjects;
    private Map<String, Integer> winConditions;
    private Map<String, Integer> loseConditions;
    private Map<String, Integer> gameConditions;
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

    public void addWinCondition(String type, Integer action) {
        winConditions.put(type, action);
    }

    public void removeWinCondition(String type, String action){
        winConditions.remove(type);
    }

    public Map<String, Integer> getWinConditions(){
    	return winConditions;
    }
    
    public void addLoseCondition(String type, Integer action) {
        loseConditions.put(type, action);
    }

    public void removeLoseCondition(String type, String action){
        loseConditions.remove(type);
    }

    public Map<String, Integer> getLoseConditions(){
    	return loseConditions;
    }
    
    public Map<String, Integer> getGameConditions(){
    	return gameConditions;
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
    
    public Settings getViewSettings(){
        return viewSettings;
    }
    
    public void setViewSettings(Settings viewSettings){
        this.viewSettings = viewSettings;
    }
}