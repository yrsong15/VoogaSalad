package objects;

import java.util.HashMap;
import java.util.Map;
import objects.interfaces.IGame;

/**
 * Created by Soravit on 11/18/2016.
 * @ author Soravit, pratiksha Sharma
 */
public class Game implements IGame{

	private String name;
	private Map<Integer, Level> levels;
	private Level currentLevel;

	public Game(String name) {
		levels = new HashMap<Integer,Level>();
		this.name = name;
	}

	public void addLevel(Level level) {
		levels.put(level.getLevel(),level);
	}

	public void removeLevel(int level) {
		levels.remove(level);
	}
	
	public Level getCurrentLevel(){
	    return this.currentLevel;
	}

	public void setCurrentLevel(Level currentLevel){
		this.currentLevel = currentLevel;
	}
	
	public void setGameName(String name){
	    this.name = name;
	}
	
	public int getNumberOfLevels(){
	   return levels.size();
	}
	
	public String getGameName(){
	    return this.name;
	}
}
