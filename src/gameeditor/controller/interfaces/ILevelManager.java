package gameeditor.controller.interfaces;

import objects.Level;

public interface ILevelManager {
	public void createLevel(int levelNumber);
	public Level getLevel();
}
