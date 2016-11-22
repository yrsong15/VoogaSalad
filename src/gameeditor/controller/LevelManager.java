package gameeditor.controller;

import gameeditor.controller.interfaces.ILevelManager;
import objects.Level;

/**
 * This is an intermediate controller that manages levels of the Game.
 * @author Ray Song(ys101)
 *
 */
public class LevelManager implements ILevelManager{
	
	private Level myLevel;

	@Override
	public void createLevel(int levelNumber) {
		Level level = new Level(levelNumber);
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
