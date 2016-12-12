package gameeditor.controller.interfaces;

import objects.GameObject;

/**
 * 
 * @author Ray Song(ys101)
 *
 */
public interface ICreateGame {
	public void createGame(String title);
	public void addCurrentLevelToGame();
	public void setCurrentLevelToGame();
	

}