package transmission;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class Receiver implements Runnable {

	private int port;
	private DatagramSocket listenerSocket = null;
	private Broadcaster broadcaster;
	private Connector connector;
	private ChatServer cs;

	public Receiver(int port, Broadcaster broadcaster, Connector connector, ChatServer cs)
			throws SocketException {
		this.port = port;
		this.broadcaster = broadcaster;
		this.connector = connector;
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
			String message = receivePacket.getAddress() + " : " + new String( receivePacket.getData());
			cs.updateMessageList(message);
			System.out.println("Received message: " + message);
			broadcaster.setDestinations(new ArrayList(connector.getConnectedUsers().keySet()));
			broadcaster.setMessage(message);
			(new Thread(broadcaster)).start();
		}
	}

}
