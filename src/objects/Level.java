package objects;
import javafx.scene.input.KeyCode;
import objects.interfaces.ILevel;
import java.security.Key;
import java.util.*;
import gameengine.view.GameEngineUI;
/**
 * Created by Soravit on 11/18/2016.
 */
public class Level implements ILevel{
	private static final int minSpacing = 250;
	private static final int maxSpacing = 500;
	private static final int pipeWidth = 300;
	private int level;
	private List<GameObject> gameObjects;
	private Map<String, String> winConditions;
	private Map<String, String> loseConditions;
	private Map<String, Double> gameConditions;
	private Map<KeyCode, String> controls;
	private List<RandomGeneration> randomGenerations;
	private GameObject mainCharacter;
	private ScrollType scrollType;
	private LevelSettings viewSettings;
	public Level(int level) {
		gameObjects = new ArrayList<GameObject>();
		randomGenerations = new ArrayList<>();
		winConditions = new HashMap<>();
		loseConditions = new HashMap<>();
		gameConditions = new HashMap<>();
		controls = new HashMap<>();
		viewSettings = new LevelSettings();
		this.level = level;
	}
	public void setScrollType(ScrollType scrollType) {
		this.scrollType = scrollType;
	}

    @Override
    public ScrollType getscrollType() {
        return this.scrollType;
    }

	public List<RandomGeneration> getRandomGenRules() {
		return randomGenerations;
	}

	public void addRandomGeneration(RandomGeneration randomGen){
		randomGenerations.add(randomGen);
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
	public void removeGameObject(int index){
		gameObjects.remove(index);
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
	public GameObject getMainCharacter() {
		return mainCharacter;
	}
	public void setMainCharacter(GameObject mainCharacter) {
		this.mainCharacter = mainCharacter;
	}
	public int getScore() {
		return gameConditions.get("score").intValue();
	}
	public void setScore(double score) {
		gameConditions.put("score", score);
	}
	public double getTime() {
		return gameConditions.get("time");
	}
	public void setTime(double time) {
		gameConditions.put("time", time);
	}
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	public LevelSettings getViewSettings() {
		return viewSettings;
	}
	
	public void setViewSettings(LevelSettings viewSettings) {
		this.viewSettings = viewSettings;
	}
	
	public void addControl(KeyCode key, String action) {
		controls.put(key, action);
	}
	public void removeControl(KeyCode key) {
		controls.remove(key);
	}
	public Map<KeyCode, String> getControls() {
		return controls;
	}

    @Override
    public void addBackgroundImage(String filePath) {
        viewSettings.setBackgroundFilePath(filePath);
    }

    @Override
    public void addBackgroundMusic(String musicFilePath) {
        viewSettings.setMusicFile(musicFilePath);
    }
    @Override
    public void addScrollWidth (double scrollWidth) {
       viewSettings.setScrollWidth(scrollWidth);
        
    }
}