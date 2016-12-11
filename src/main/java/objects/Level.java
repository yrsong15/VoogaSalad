package objects;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.input.KeyCode;
import java.security.Key;
import java.util.*;

import gameengine.model.RandomGenFrame;
import gameengine.view.GameEngineUI;

/**
 * Created by Soravit on 11/18/2016.
 * @author : Soravit, Pratiksha
 */
public class Level {

	private int level;
	private List<GameObject> projectiles;
    private List<GameObject> gameObjects;
    private List<GameObject> obstacles;
    private Map<String, String> winConditions;
	private Map<String, String> loseConditions;
	private Map<String, Double> gameConditions;
    private RandomGenFrame<Integer> randomGenerationFrame;
    private String musicFilePath;
	private String backgroundFilePath;
	private List<GameObject> players;
	private ScrollType scrollType;

	public Level(int level) {
        this.level = level;
        projectiles = new ArrayList<GameObject>();
        gameObjects = new ArrayList<GameObject>();
        obstacles = new ArrayList<GameObject>();
        players = new ArrayList<>();
		winConditions = new HashMap<>();
		loseConditions = new HashMap<>();
		gameConditions = new HashMap<>();
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
		return randomGenerationFrame.getRandomGenerationRules();
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

	public Map<String, Double> getGameConditions() {
		return gameConditions;
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

	public int getScore() {
        if(gameConditions.get("score") == null){
            gameConditions.put("score", 0.0);
        }
		return gameConditions.get("score").intValue();
	}

	public void setScore(double score) {
		gameConditions.put("score", score);
	}

	public double getTime() {
        if(gameConditions.get("time") == null){
            gameConditions.put("time", 0.0);
        }
		return gameConditions.get("time");
	}

	public void setTime(double time) {
		gameConditions.put("time", time);
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

    public void setBackgroundImage(String filePath) {
        this.backgroundFilePath = filePath;
    }

    public void setBackgroundMusic(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }

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


}