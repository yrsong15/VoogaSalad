package gameeditor.controller.interfaces;

import objects.GameObject;
import objects.Level;

/**
 * 
 * @author Ray Song(ys101)
 *
 */
public interface ICreateLevel {
	public void createLevel(int levelNumber);
	public void addCurrentGameObjectToLevel();
	public void addWinConditions(String type, String action);
	public void addLoseConditions(String type, String action);
}
