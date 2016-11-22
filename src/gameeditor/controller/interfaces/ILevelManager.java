package gameeditor.controller.interfaces;

import objects.Level;

public interface ILevelManager {
	public void createLevel(int levelNumber);
	public void addWinConditions(String type, String action);
	public void addLoseConditions(String type, String action);
	public Level getLevel();
}
