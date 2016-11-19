package objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soravit on 11/18/2016.
 */
public class Game {

	private String name;
	private Map<Integer, Level> levels;
	private Level currentLevel;

	public Game(String name) {
		levels = new HashMap<Integer,Level>();
		this.name = name;
	}

	public void addLevel(Level level) {
		levels.put( level.getLevel(),level);
	}

	public void removeLevel(int level) {
		levels.remove(level);
	}
	
	public Level getCurrentLevel(){
		return currentLevel;
	}
}
