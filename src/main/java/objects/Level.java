package objects;


import objects.interfaces.ILevel;
import java.util.*;
import gameengine.model.RandomGenFrame;
import gameengine.model.boundary.GameBoundary;
/**
 * Created by Soravit on 11/18/2016.
 * @author : Soravit, Pratiksha, Chalena
 */
public class Level implements ILevel{

    private int level;
    private List<GameObject> projectiles;
    private List<GameObject> gameObjects;
    private List<GameObject> obstacles;
    private Map<String, String> winConditions;
    private Map<String, String> loseConditions;
    private List<RandomGeneration> randomGenerations;
    private String musicFilePath, backgroundFilePath, title;
    private RandomGenFrame<Integer> randomGenerationFrame;
    private List<GameObject> players;
    private ScrollType scrollType;
    private GameObject background;
    private int time = 0;


    public Level(int level) {
        this.level = level;
        projectiles = new ArrayList<GameObject>();
        gameObjects = new ArrayList<GameObject>();
        obstacles = new ArrayList<GameObject>();
        players = new ArrayList<>();

        winConditions = new HashMap<>();
        loseConditions = new HashMap<>();
    }

    public void removeAllPlayers(){
        players = new ArrayList<>();
    }    

    public void setScrollType(ScrollType scrollType) {
        this.scrollType = scrollType;
    }

    public ScrollType getScrollType() {
        return this.scrollType;
    }

    public RandomGenFrame getRandomGenerationFrame(){
        return randomGenerationFrame;
    }

    public ArrayList<RandomGeneration<Integer>> getRandomGenRules() {
    	try {
            return randomGenerationFrame.getRandomGenerationRules();
    	} catch (NullPointerException npe){
    		return new ArrayList<RandomGeneration<Integer>>();
    	}
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public void setRandomGenerationFrame(RandomGenFrame<Integer> randomGen) {
        randomGenerationFrame = randomGen;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addProjectile(GameObject go) {
        projectiles.add(go);
    }

    public void removeProjectile(GameObject go) {
        projectiles.remove(go);
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

    public void removeWinCondition(String type, String action) {
        winConditions.remove(type);
    }

    public Map<String, String> getWinConditions() {
        return winConditions;
    }

    public void addLoseCondition(String type, String action) {
        loseConditions.put(type, action);
    }

    public void removeLoseCondition(String type, String action) {
        loseConditions.remove(type);
    }

    public Map<String, String> getLoseConditions() {
        return loseConditions;
    }

    public void addPlayer(GameObject player){
        players.add(player);
    }
    
    public void removePlayer(GameObject player){
    	players.remove(player);
    }

    public List<GameObject> getPlayers(){
        return players;
    }

    public List<GameObject> getProjectiles(){
        return projectiles;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<GameObject> getObstacles() {
        return obstacles;
    }

    public GameObject getBackground(){
        return background;
    }

    public void setBackgroundObject(){
        GameBoundary gameBoundaries = this.getScrollType().getGameBoundary();
        background = new GameObject(0, 0, 0, gameBoundaries.getWorldWidth(), gameBoundaries.getWorldHeight(), backgroundFilePath, new HashMap<>());
    }

    public void setBackgroundImage(String filePath) {
        this.backgroundFilePath = filePath;
    }

    public void setBackgroundMusic(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }

    public void setTitle(String title){ this.title = title; }

    public List<GameObject> getAllGameObjects(){
        List<GameObject> allObjects = new ArrayList<>();
        allObjects.addAll(players);
        allObjects.addAll(gameObjects);
        allObjects.addAll(projectiles);
        return allObjects;
    }

    public String getMusicFilePath(){
        return musicFilePath;
    }

    public String getBackgroundFilePath(){
        return backgroundFilePath;
    }

    public String getTitle(){ 
        return title; 
    }
}