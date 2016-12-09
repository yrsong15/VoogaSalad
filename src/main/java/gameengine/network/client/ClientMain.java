package gameengine.network.client;

import java.util.ArrayList;
import java.util.List;

import gameengine.controller.interfaces.ControlInterface;
import objects.Game;
import objects.GameObject;
import objects.Player;

/**
 * 
 * @author Titas Skrebe
 *
 *         This is the main class of a client side for an online multiplayer
 *         game.
 * 
 *         Go to www.tskrebe.me for more info
 * 
 */
public class ClientMain implements ControlInterface {

	static long ID = -1; // we get ID from the server side

	private TcpConnection connections; // establishing TCP connection

	private String server_ip;
	private int server_port_tcp;
	private int client_port_udp;

	public ClientMain(String ip, int portTcp, int portUdp) {
		server_ip = ip;
		// server_port_tcp = portTcp;
		server_port_tcp = 9090;
		client_port_udp = portUdp;

		connections = new TcpConnection(this, server_ip, server_port_tcp);

		if ((ID = connections.getIdFromServer()) == -1) {
			System.err.println("cant get id for char");
		}

		new Thread(new UdpConnection(this, connections, client_port_udp)).start();
	}

	/** Function to send main characters data to server */
	public void sendCharacterCommand(String command) {
		connections.sendCommand(command);
	}

	/** Closing game */
	public void closingOperations() {
		connections.removeCharacter(ID);
	}

	/**
	 * Getting info about game play
	 * 
	 * @param objects
	 *            Object can be either bullet or player
	 */
	public Game updateGame(Game game) {
		return game;
	}

	@Override
	public void moveUp(GameObject player, double speed) {
		sendCharacterCommand(this.getClass().getEnclosingMethod().getName() + " = " + Double.toString(speed));
	}

	@Override
	public void moveDown(GameObject player, double speed) {
		sendCharacterCommand(this.getClass().getEnclosingMethod().getName() + " = " + Double.toString(speed));
	}

	@Override
	public void moveRight(GameObject player, double speed) {
		sendCharacterCommand(this.getClass().getEnclosingMethod().getName() + " = " + Double.toString(speed));
	}

	@Override
	public void moveLeft(GameObject player, double speed) {
		sendCharacterCommand(this.getClass().getEnclosingMethod().getName() + " = " + Double.toString(speed));
	}

	@Override
	public void jump(GameObject player, double speed) {
		sendCharacterCommand(this.getClass().getEnclosingMethod().getName() + " = " + Double.toString(speed));
	}

	@Override
	public void shootProjectile(GameObject player, double speed) {
		sendCharacterCommand(this.getClass().getEnclosingMethod().getName() + " = " + Double.toString(speed));
	}
}
