package transmission;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import model.Message;

public class Receiver implements Runnable {

	private int port;
	private DatagramSocket listenerSocket = null;
	//private Broadcaster broadcaster;
	//private Connector connector;
	private ChatServer cs;

	public Receiver(int port, ChatServer cs)
			throws SocketException {
		this.port = port;
		//this.broadcaster = broadcaster;
		//this.connector = connector;
		this.cs = cs;
		listenerSocket = new DatagramSocket(port);
	}

	@Override
	public void run() {
		while (true) {
			System.out.println("Receiving");
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String receivedMessage = (new String(receivePacket.getData())).trim();
			InetAddress senderIp = receivePacket.getAddress();
			int senderPort = receivePacket.getPort();
			Message m = new Message(receivedMessage, senderIp, senderPort);
			cs.updateMessageList(m);
			System.out.println("Received message: " + m);
			//broadcaster.setDestinations(new ArrayList(connector.getConnectedUsers().keySet()));
			//broadcaster.setMessage(message);
			//(new Thread(broadcaster)).start();
		}
	}

}
