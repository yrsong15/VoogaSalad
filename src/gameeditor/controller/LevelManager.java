package gameeditor.controller;
import gameeditor.controller.interfaces.ILevelManager;
import objects.Level;
import objects.LevelSettings;

/**
 * This is an intermediate controller that manages levels of the Game.
 * @author Ray Song(ys101)
 *
 */
public class LevelManager implements ILevelManager{
	private Level myLevel;
	//private LevelSettings mySettings;
	//private Settings mySettings;
	
	@Override
	public void createLevel(int levelNumber) {
		Level level = new Level(levelNumber);
		//mySettings = new LevelSettings(null,null,null);
		myLevel = level;
	}

	public Level getLevel() {
		return myLevel;
	}
	

	@Override
	public void addWinConditions(String type, String action) {
		myLevel.addWinCondition(type, action);
	}

	@Override
	public void addLoseConditions(String type, String action) {
		myLevel.addLoseCondition(type, action);
	}
	

}
