package gameengine.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.xml.bind.JAXBException;

import gameengine.network.ServerMessage;
import xml.XMLSerializer;

class TcpConnection {

	private static final int GET_ID = 0;
	private static final int SEND_COMMAND = 1;
	private static final int GET_ID_IP_PORT = 2;
	private static final int REMOVE_CHARACTER = 3;
	
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
			e.printStackTrace();
		}
	}
	
	/** Gets unique ID for player **/
	long getIdFromServer() {
		
		try {
			ServerMessage sm = new ServerMessage(GET_ID);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			ID = ois.readLong();
			return ID;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/** Sends data about the main character to server. Velocity, etc. */
	void sendCommand(String command, int charIdx) {
		try {
			ServerMessage sm = new ServerMessage(SEND_COMMAND);
			sm.setCommand(command);
			sm.setCharIdx(charIdx);
			sm.setId(ID);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Sends IP and port of Udp connection **/
	void sendIpIdPort(int port) {
		
		try {
			ServerMessage sm = new ServerMessage(GET_ID_IP_PORT);
			sm.setPort(port);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Sends id of player to the server to inform that a player has left the game **/
	void removeCharacter(long id) {
		
		try {
			ServerMessage sm = new ServerMessage(REMOVE_CHARACTER);
			sm.setId(id);
			String data = serializer.serializeServerMessage(sm);
			oos.writeObject(data);
			//oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
