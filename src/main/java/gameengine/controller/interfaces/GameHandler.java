// This entire file is part of my masterpiece.
// Ray Song (ys101)
// The changes made in this class demonstrate my understanding and flexible use of Java interfaces to
// toggle methods that exist in GameEngineBackend.java through the GameHandler interface.
package gameengine.controller.interfaces;

import objects.ClientGame;
import objects.Game;

/**
 * 
 * @author Ray Song, Eric Song
 *
 */
public interface GameHandler {

	public Game getGame();
	public void addClientCharacter();
	public void updateGame();
	public void runControl(String controlName, int ID, int charIdx);
	public ClientGame getClientGame();
	public void addPlayersToClient(int ID);
	public void removePlayer(int ID);
	public void restart();
}
