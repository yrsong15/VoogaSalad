package gameengine.network.client;

import java.util.ArrayList;
import java.util.List;

import gameengine.controller.interfaces.ControlInterface;
import gameengine.network.server.UDPHandler;
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
public class ClientMain{

	public static long ID = -1; // we get ID from the server side

	private TcpConnection connections; // establishing TCP connection

	private String server_ip;
	private int server_port_tcp;
	private int client_port_udp;
	private UDPHandler handler;

	public ClientMain(String ip, int portTcp, int portUdp, UDPHandler handler) {
		server_ip = ip;
		this.handler = handler;
		// server_port_tcp = portTcp;
		server_port_tcp = 9090;
		client_port_udp = portUdp;

		connections = new TcpConnection(this, server_ip, server_port_tcp);

		if ((ID = connections.getIdFromServer()) == -1) {
			System.err.println("cant get id for char");
		}

		new Thread(new UdpConnection(this, connections, client_port_udp, handler)).start();
	}

	/** Function to send main characters data to server */
	public void sendCharacterCommand(String command, int charIdx) {
		connections.sendCommand(command, charIdx);
	}

	/** Closing game */
	public void closingOperations() {
		connections.removeCharacter(ID);
	}
	
	public long getID(){
		return ID;
	}


//	public Game updateGame(Game game) {
//		return game;
//	}


	public void moveUp(GameObject player, double speed) {
		// sendCharacterCommand(this.getClass().getEnclosingMethod().getName() +
		// " = " + Double.toString(speed));
		
		sendCharacterCommand("moveUp", handler.getCharIdx(player));
	}

	public void moveDown(GameObject player, double speed) {
		// sendCharacterCommand(this.getClass().getEnclosingMethod().getName() +
		// " = " + Double.toString(speed));
		sendCharacterCommand("moveDown", handler.getCharIdx(player));
	}

	public void moveRight(GameObject player, double speed) {
		// sendCharacterCommand(this.getClass().getEnclosingMethod().getName() +
		// " = " + Double.toString(speed));
		sendCharacterCommand("moveRight", handler.getCharIdx(player));
	}

	public void moveLeft(GameObject player, double speed) {
		// sendCharacterCommand(this.getClass().getEnclosingMethod().getName() +
		// " = " + Double.toString(speed));
		sendCharacterCommand("moveLeft", handler.getCharIdx(player));
	}

	public void jump(GameObject player, double speed) {
		// sendCharacterCommand(this.getClass().getEnclosingMethod().getName() +
		// " = " + Double.toString(speed));
		sendCharacterCommand("jump", handler.getCharIdx(player));
	}

	public void shootProjectile(GameObject player, double speed) {
		// sendCharacterCommand(this.getClass().getEnclosingMethod().getName() +
		// " = " + Double.toString(speed));
//		sendCharacterCommand(this.getClass().getEnclosingMethod().getName());
		sendCharacterCommand("shootProjectile", handler.getCharIdx(player));
	}
	
	public void pause(){
		connections.sendPauseCommand();
	}
}
