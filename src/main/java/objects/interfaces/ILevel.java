package objects.interfaces;

import javafx.scene.input.KeyCode;
import objects.GameObject;
import objects.RandomGeneration;
import objects.ScrollType;
import objects.interfaces.ILevel;

import java.security.Key;
import java.util.*;

public interface ILevel {

    public void setScrollType(ScrollType scrollType);
    
    public int getLevel();

    public void setLevel(int level) ;

    public void addGameObject(GameObject go);

    public void removeGameObject(GameObject go);

    public void addWinCondition(String type, String action);

	public void removeWinCondition(String type, String action);
	public Map<String, String> getWinConditions();
	
	
	public void addLoseCondition(String type, String action) ;
	
	public void removeLoseCondition(String type, String action);
	
	public Map<String, String> getLoseConditions();
	
	public Map<String, Double> getGameConditions();
	
	public GameObject getMainCharacter() ;
	
	public void setMainCharacter(GameObject mainCharacter) ;
	
	public int getScore();
	
	public void setScore(double score);
	
	public double getTime();
	
	public void setTime(double time);
	
	public List<GameObject> getGameObjects();
	
	public void setControl(KeyCode key, String action);
	
	public void removeControl(KeyCode key);
	
	public Map<KeyCode, String> getControls();

	public void setBackgroundImage(String filePath);
	
	public void setBackgroundMusic(String musicFilePath);

	public void addScrollWidth(double scrollWidth);

    public void addRandomGeneration (RandomGeneration randomGeneration);
    public String getMusicFilePath();
    public String getBackgroundFilePath();


}


