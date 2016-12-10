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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.JAXBException;

import gameeditor.xml.XMLSerializer;
import gameeditor.xml.XMLTrimmer;
import gameengine.controller.interfaces.GameHandler;
import objects.Game;

public class ServerMain {

	// refreshing game state and sending data to clients every x ms
//	private static final long RESHRESH_GAP = 30;
	private static final long RESHRESH_GAP = 50;


	private static int SERVER_PORT_TCP;

	private static long IDs = 0L;

	private XMLSerializer serializer;

	// thread safe array because while one thread is reading another
	// might add delete some entries
	private CopyOnWriteArrayList<IpPort> activeClients;

	private UdpConnectionsSend udpSend;

	private GameHandler gameHandler;

	public ServerMain(GameHandler gameHandler, int tcpPort) {
		this.gameHandler = gameHandler;
		SERVER_PORT_TCP = tcpPort;
		activeClients = new CopyOnWriteArrayList<IpPort>();
		udpSend = new UdpConnectionsSend();
		serializer = new XMLSerializer();
		start();
	}

	private void start() {

		gameStateRefresher();

//		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_TCP, 0, InetAddress.getByName("25.16.229.50"))) {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_TCP, 0, InetAddress.getByName("localhost"))) {

			Socket clientSocket;
			while ((clientSocket = serverSocket.accept()) != null) {
				new Thread(new TcpConnection(this, clientSocket)).start();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void gameStateRefresher() {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				gameHandler.updateGame();
				udpSend.sendGamePlay(gameHandler.getGame());
			}

		}, 0, RESHRESH_GAP);
	}

	synchronized long getId() {
		return IDs++;
	}

	void removeCharacter(long id) {

	}

	void readCommand(long id, String command) {
		gameHandler.runControl(command);
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
		boolean a;

		public UdpConnectionsSend() {

			try {
				gamePlaySocket = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		public void sendGamePlay(Game game) {

			try {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(XMLTrimmer.trim(serializer.serializeGame(game)));
				byte[] bytes = baos.toByteArray();
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