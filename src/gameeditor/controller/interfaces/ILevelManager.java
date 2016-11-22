package gameeditor.controller.interfaces;

import objects.GameObject;

/**
 * 
 * @author Ray Song(ys101)
 *
 */
public interface ILevelManager {
	public void createLevel(int levelNumber);
	public void addGameObjectToLevel(GameObject ob);
	public void addWinConditions(String type, String action);
	public void addLoseConditions(String type, String action);
	public void addScore(double score);
	public void addTime(double time);
	public void addBackgroundMusic(String musicFilePath);
	public void addBackgroundImage(String backgroundFilePath);
	public void setGameObjectToMainCharacter(GameObject object);
}
