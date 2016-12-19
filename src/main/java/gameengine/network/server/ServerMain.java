package gameengine.network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import gameengine.controller.interfaces.GameHandler;
import objects.ClientGame;
import xml.XMLSerializer;
import xml.XMLTrimmer;

/**
 * 
 * @author Titas Skrebe
 * 
 * Edited by Eric Song, Ray Song
 *
 *
 */
public class ServerMain {

	// refreshing game state and sending data to clients every x ms
	public static int idCounter = 0;

	private static final long REFRESH_GAP = 30;

	private static int SERVER_PORT_TCP;

	private static int IDs = 0;

	private XMLSerializer serializer;
	private Timer timer;
	private boolean isPaused;

	// thread safe array because while one thread is reading another
	// might add delete some entries
	private CopyOnWriteArrayList<IpPort> activeClients;

	private UdpConnectionsSend udpSend;

	private GameHandler gameHandler;

	private ServerSocket serverSocket;
	
	public ServerMain(GameHandler gameHandler, int tcpPort, String serverName) {

		this.gameHandler = gameHandler;
		SERVER_PORT_TCP = tcpPort;
		activeClients = new CopyOnWriteArrayList<IpPort>();
		udpSend = new UdpConnectionsSend();
		serializer = new XMLSerializer();
		isPaused = false;
		start(serverName);
	}

	private void start(String serverName) {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_TCP, 0, InetAddress.getByName(serverName))) {
			this.serverSocket = serverSocket;
			Socket clientSocket;
			while ((clientSocket = serverSocket.accept()) != null) {
				new Thread(new TcpConnection(this, clientSocket)).start();
				gameStateRefresher();
			}
		} 
		catch (IOException ex) {
		}

	}

	private void gameStateRefresher() {
		timer = new Timer();
		runTimer();
	}
	
	public void pause(){
		isPaused = !isPaused;
	}
	
	public void restart(){
		gameHandler.restart();
	}
	
	public void shutdownServerThread(){
		try {
			serverSocket.close();
		} catch (IOException ex) {
		}
		Thread.currentThread().interrupt();
		return;
	}
	
	private void runTimer(){
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if(!isPaused && (IDs >= gameHandler.getGame().getMinNumPlayers())){
					gameHandler.updateGame();
				}
				udpSend.sendGamePlay(gameHandler.getClientGame());
			}

		}, 0, REFRESH_GAP);
	}

	synchronized long getId() {
		gameHandler.addPlayersToClient(IDs);
		return IDs++;
	}

	void removeCharacter(long id) {

	}

	void readCommand(String command,int id, int charIdx) {
		gameHandler.runControl(command, id, charIdx);
	}

	void addressBook(InetAddress address, int port) {
		activeClients.add(new IpPort(address, port));
		gameHandler.addClientCharacter();
	}

	private static class IpPort {

		InetAddress address;
		int port;

		public IpPort(InetAddress address, int port) {
			this.address = address;
			this.port = port;
		}
	}

	private class UdpConnectionsSend {

		DatagramSocket gamePlaySocket;

		public UdpConnectionsSend() {

			try {
				gamePlaySocket = new DatagramSocket();
			} catch (SocketException e) {
			}
		}

		public void sendGamePlay(ClientGame game) {

			try {
				byte[] bytes = XMLTrimmer.trim(serializer.serializeClientGame(game)).getBytes();
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

				for (IpPort dest : activeClients) {
					packet.setAddress(dest.address);
					packet.setPort(dest.port);
					gamePlaySocket.send(packet);
					packet.setData(bytes);
					packet.setLength(bytes.length);
				}

			} catch (IOException e) {

			}
		}

	}
}