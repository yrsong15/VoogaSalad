package gameeditor.controller;

import gameeditor.controller.interfaces.ILevelManager;
import objects.Level;

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
    
}
