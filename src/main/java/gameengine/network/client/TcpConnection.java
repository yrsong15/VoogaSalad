// This entire file is part of my masterpiece.
// Ray Song (ys101)
// Lines 121-131
// This class demonstrates the use of my enum, with which I send/receive predefined commands over the TCP network.

package gameengine.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gameengine.network.ServerMessage;
import gameengine.network.TcpCommand;
import xml.XMLSerializer;

/**
 * 
 * @author Titas Skrebe
 * 
 * Edited by Eric Song, Ray Song
 * 
 */
class TcpConnection {
	
	private final int SERVER_PORT_TCP;
	private final String SERVER_IP;

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private XMLSerializer serializer;
	
	private Socket socket;
	private long ID;

	TcpConnection(ClientMain main, String ip, int port) {
		
		SERVER_PORT_TCP = port;
		SERVER_IP = ip;
		serializer = new XMLSerializer();
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT_TCP);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
		}
	}
	
	/** Gets unique ID for player **/
	long getIdFromServer() {
		
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.GET_ID);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			ID = ois.readLong();
			return ID;
		} catch (IOException e) {
		}
		return -1;
	}
	
	/** Sends data about the main character to server. Velocity, etc. */
	void sendCommand(String command, int charIdx) {
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.SEND_COMMAND);
			sm.setCommand(command);
			sm.setCharIdx(charIdx);
			sm.setId(ID);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			oos.reset();
		} catch (IOException e) {
		}
	}
	
	void sendPauseCommand(){
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.PAUSE);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			oos.reset();
		} catch (IOException ex) {
		}
		
	}
	
	void sendRestartCommand(){
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.RESTART);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			oos.reset();
		} catch (IOException ex) {
		}
		
	}
	
	void sendShutdownCommand(){
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.SERVER_THREAD_SHUTDOWN);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			oos.reset();
		} catch (IOException ex) {
		}
		
	}
	
	/** Sends IP and port of Udp connection **/
	void sendIpIdPort(int port) {
		
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.GET_ID_IP_PORT);
			sm.setPort(port);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
		} catch (IOException e) {
		}
	}
	
	//This method is part of my code masterpiece (lines 121-131).
	/** Sends id of player to the server to inform that a player has left the game **/
	void removeCharacter(long id) {
		
		try {
			ServerMessage sm = new ServerMessage(TcpCommand.REMOVE_CHARACTER);
			sm.setId(id);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
		} catch (IOException e) {
		}
	}
	
	Socket getSocket(){
		return socket;
	}

}
