package gameengine.network.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

import javax.xml.bind.JAXBException;

import gameengine.network.server.UDPHandler;
import objects.ClientGame;
import objects.Game;
import objects.GameObject;
import xml.XMLSerializer;

/**
* This class establishes UDP connection with server and receives data about
* the game state
*/
class UdpConnection implements Runnable {
	
		private ClientMain main;
		
		private byte[] buffer = new byte[1024 * 10];
		
		private DatagramSocket datagramSocket;
		
		private TcpConnection tcpConnection;
		XMLSerializer serializer;
		
		//set udp port you want get game-play though. Make sure
		//router port forwards it.
		//private final int UDP_PORT;

		private final int UDP_PORT;
		private UDPHandler udpHandler;

		UdpConnection(ClientMain main, TcpConnection tcpConnection, int client_port_udp, UDPHandler handler) {
			udpHandler = handler;
			this.main = main;
			this.tcpConnection = tcpConnection;
			UDP_PORT = client_port_udp;
			serializer = new XMLSerializer();
		}

		/** Listens to server, reads sent data and passes it to main class */
		@Override
		public void run() {

			try {
				if (UDP_PORT < 0 || UDP_PORT > 65535){
					datagramSocket = new DatagramSocket();
					System.err.append(UDP_PORT + "port is not possible. Random port assigned");
				}
				else{
					datagramSocket = new DatagramSocket(UDP_PORT);
				}
				// send info about UDP to server
				tcpConnection.sendIpIdPort(datagramSocket.getLocalPort());
				System.err.println(datagramSocket.getLocalPort());
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true) {

					String data;
					try {
						datagramSocket.receive(packet);
						ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
//						ObjectInputStream ois = new ObjectInputStream(bais);
//						data = (String) ois.readObject();
						BufferedReader bfReader = new BufferedReader(new InputStreamReader(bais));
						data = bfReader.readLine();
						System.out.println(data);
						String endTag = "</objects.ClientGame>";
						int end = data.indexOf(endTag);
						data = data.substring(0, end+endTag.length());
						System.out.println(data.length());
					} catch (IOException e1) {
						e1.printStackTrace();
						continue;
					}
					ClientGame game = null;
					try {
						game = serializer.getClientGameFromString(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
					udpHandler.updateGame(game);
					packet.setData(buffer);
					packet.setLength(buffer.length);
				}

			} catch ( SocketException e) {
				e.printStackTrace();
			}

		}

}
