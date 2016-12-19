package gameengine.controller.interfaces;

import objects.ClientGame;
import objects.Game;

/**
 * @author Eric Song
 */
public interface GameHandler {

	/**
	 * @return Game object
	 */
	public Game getGame();

	/**
	 * run a game loop iteration to update the game
	 */
	public void updateGame();

	/**
	 * @param controlName
	 *            String representation of the control name to process
	 * @param ID
	 *            identifies the ID of the client sending the message
	 * @param charIdx
	 *            identifies which main character to perform the action on the
	 *            client ID
	 */
	public void runControl(String controlName, int ID, int charIdx);

	/**
	 * @return client version of the game
	 */
	public ClientGame getClientGame();

	/**
	 * @param ID add all players of client ID to the game
	 */
	public void addPlayersToClient(int ID);

	/**
	 * restart game
	 */
	public void restart();

	/**
	 * @param ID remove all characters associated with client ID
	 */
	public void removeCharacters(long ID);
}
