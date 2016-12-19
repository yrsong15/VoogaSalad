// This entire file is part of my masterpiece.
// Eric Song
//
// This class handles the server-related functionality of the Game Engine back-end.
// I believe it showcases my ability to properly utilize interfaces, error handling,
// lambda expressions, nested classes, and tight restrictions on access modifiers.
// I also would like to showcase my newfound knowledge of multithreading that I
// implemented for online multiplayer.

package gameengine.network.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.JAXBException;

import exception.GameEngineServerSideException;
import gameengine.controller.interfaces.GameHandler;
import objects.ClientGame;
import objects.Game;
import xml.XMLSerializer;
import xml.XMLTrimmer;

/**
 * @author Eric Song, Ray Song, Titas Skrebe
 */
public class ServerMain implements MessageHandler {

	// refreshing game state and sending data to clients every x ms
	private final long REFRESH_GAP = 30;
	private int tcpPort;
	private int numPlayers = 0;
	private XMLSerializer serializer;
	private Timer timer;
	private boolean gameIsPaused;
	private UdpConnection udpConnection;
	private GameHandler gameHandler;
	private ServerSocket serverSocket;
	private String serverName;
	// thread safe array because while one thread is reading another
	// might add delete some entries
	private CopyOnWriteArrayList<IpPort> activeClients;

	/**
	 * @param gameHandler
	 *            handles all game-related functionality
	 * @param tcpPort
	 *            port number for TCP connection to send data through
	 * @param serverName
	 *            either "localhost" or a valid IP address
	 * @throws GameEngineServerSideException
	 *             if UDP connection is not successfully established
	 */
	public ServerMain(GameHandler gameHandler, int tcpPort, String serverName) throws GameEngineServerSideException {
		this.gameHandler = gameHandler;
		this.serverName = serverName;
		this.tcpPort = tcpPort;

		activeClients = new CopyOnWriteArrayList<IpPort>();
		udpConnection = new UdpConnection();
		serializer = new XMLSerializer();
		gameIsPaused = false;
	}

	/**
	 * @throws GameEngineServerSideException
	 *             if server socket is not successfully created
	 */
	public void start() throws GameEngineServerSideException {
		try (ServerSocket serverSocket = new ServerSocket(tcpPort, 0, InetAddress.getByName(serverName))) {
			this.serverSocket = serverSocket;
			Socket clientSocket;
			while ((clientSocket = serverSocket.accept()) != null) {
				new Thread(new TcpConnection(this, clientSocket)).start();
				gameStateRefresher();
			}
		} catch (IOException e) {
			throw new GameEngineServerSideException("Error in creating ServerSocket");
		}
	}

	private void gameStateRefresher() {
		timer = new Timer();
		// use lambda and wrapper class to create new thread that runs the
		// TimerTask
		timer.scheduleAtFixedRate(wrap(() -> updateGameAndSendData()), 0, REFRESH_GAP);
	}

	private TimerTask wrap(Runnable r) {
		return new TimerTask() {
			@Override
			public void run() {
				r.run();
			}
		};
	}

	private void updateGameAndSendData() {
		if (!gameIsPaused && (numPlayers >= gameHandler.getGame().getMinNumPlayers())) {
			gameHandler.updateGame();
		}

		try {
			udpConnection.sendGamePlay(gameHandler.getClientGame());
		} catch (GameEngineServerSideException e) {
			// Print error on the separate timer thread
			String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			System.err.println("Exception thrown on " + date + " with message: " + e.getMessage());
		}

	}

	@Override
	public void pause() {
		gameIsPaused = !gameIsPaused;
	}

	@Override
	public void restart() {
		gameHandler.restart();
	}

	@Override
	public void shutdownServerThread() throws GameEngineServerSideException {
		try {
			serverSocket.close();
		} catch (IOException ex) {
			throw new GameEngineServerSideException("Error in closing ServerSocket");
		}
		Thread.currentThread().interrupt();
		return;
	}

	// Add synchronized modifier so that two client instances do not ever call
	// the method at the same time. Concurrent calling of this method in two
	// instances could cause the clients to get the same ID and then skip over
	// the next ID due to double incrementation
	@Override
	public synchronized long getId() {
		gameHandler.addPlayersToClient(numPlayers);
		return numPlayers++;
	}

	@Override
	public void removeCharacters(long id) {
		gameHandler.removeCharacters(id);
	}

	@Override
	public void readCommand(String command, int id, int charIdx) {
		gameHandler.runControl(command, id, charIdx);
	}

	@Override
	public void addIpPort(InetAddress address, int port) {
		activeClients.add(new IpPort(address, port));
	}

	// Below are two nested class that are only used locally. This increases
	// encapsulation and makes code more readable and maintainable

	// Class that encapsulates client info
	private class IpPort {
		InetAddress address;
		int port;

		public IpPort(InetAddress address, int port) {
			this.address = address;
			this.port = port;
		}
	}

	// Class used to send UDP packets to clients
	private class UdpConnection {

		DatagramSocket gamePlaySocket;

		public UdpConnection() throws GameEngineServerSideException {

			try {
				gamePlaySocket = new DatagramSocket();
			} catch (SocketException e) {
				throw new GameEngineServerSideException("Error in creating UDP Socket");
			}
		}

		/**
		 * @param game
		 *            client game object
		 * @throws GameEngineServerSideException
		 *             if data is not successfully sent
		 * 
		 *             Sends game information that the client needs (only
		 *             front-end related data that the clients need such as
		 *             image names and positions of objects)
		 */
		public void sendGamePlay(ClientGame game) throws GameEngineServerSideException {

			try {
				byte[] bytes = XMLTrimmer.trim(serializer.serializeClientGame(game)).getBytes();
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
				for (IpPort dest : activeClients) {
					packet.setAddress(dest.address);
					packet.setPort(dest.port);
					gamePlaySocket.send(packet);
				}

			} catch (IOException e) {
				throw new GameEngineServerSideException("Error in sending data to client(s)");
			}
		}

	}
}