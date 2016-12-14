package gameengine.network.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.xml.bind.JAXBException;

import gameengine.network.ServerMessage;
import xml.XMLSerializer;

/**
 * This class establishes TCP connection and listens to client side
 * for tasks to do.
 * 
 *  * @author Titas Skrebe
 * 
 * Edited by Eric Song, Ray Song
 * 
 */
class TcpConnection implements Runnable{
	
	private static final int GET_ID = 0;
	private static final int SEND_COMMAND = 1;
	private static final int GET_ID_IP_PORT = 2;
	private static final int REMOVE_CHARACTER = 3;
	private static final int PAUSE = 4;
	private static final int RESTART = 5;
	private static final int SERVER_THREAD_SHUTDOWN = 6;

	private ServerMain main;
	private Socket socket;
	private XMLSerializer serializer;
	
	TcpConnection(ServerMain main, Socket socket) {
		this.main = main;
		this.socket = socket;
		serializer = new XMLSerializer();
	}
	
	@Override
	public void run() {
		try(ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())){
			while(true){
				String msg = (String)ois.readObject();
				ServerMessage sm;
				try {
					sm = serializer.getServerMessageFromString(msg);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				switch(sm.messageType){
					case GET_ID:
						oos.writeLong(main.getId());
						break;
					case SEND_COMMAND:
						main.readCommand(sm.command,(int)sm.id,sm.charIdx);
						break;
					case GET_ID_IP_PORT: 
						String ipString = socket.getInetAddress().getHostName();
						InetAddress clientIp = InetAddress.getByName(ipString);
						System.err.println(ipString + " " + clientIp);
						main.addressBook(clientIp, sm.port);
						break;
					case REMOVE_CHARACTER:
						main.removeCharacter(sm.id);
						break;
					case PAUSE:
						main.pause();
						break;
					case RESTART:
						main.restart();
						break;
					case SERVER_THREAD_SHUTDOWN:
						main.shutdownServerThread();
						break;
					default:
						break;
				}
				oos.flush();
				
			}
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}

}
