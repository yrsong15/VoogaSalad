// This entire file is part of my masterpiece.
// Eric Song
//
// This is the interface that ServerMain implements, and it is included in the masterpiece for javadoc purposes

package gameengine.network.server;

import java.net.InetAddress;

import exception.GameEngineServerSideException;

public interface MessageHandler {

	/**
	 * @return next available ID to assign to client
	 */
	public long getId();

	/**
	 * @param command
	 *            String representation of the control name to process
	 * @param id
	 *            identifies the ID of the client sending the message
	 * @param charIdx
	 *            identifies which main character to perform the action on the
	 *            client ID
	 */
	public void readCommand(String command, int id, int charIdx);

	/**
	 * @param address
	 *            of the client
	 * @param port
	 *            that the client is connecting to
	 */
	public void addIpPort(InetAddress address, int port);

	/**
	 * @param id
	 *            remove all characters associated with client with ID
	 */
	public void removeCharacters(long id);

	/**
	 * Pauses the game
	 */
	public void pause();

	/**
	 * Restarts the game
	 */
	public void restart();

	/**
	 * @throws GameEngineServerSideException
	 *             if server socket is unable to close
	 */
	public void shutdownServerThread() throws GameEngineServerSideException;
}
