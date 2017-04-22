package transmission;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

import statics.Values;

public class Broadcaster implements Runnable {
	
	private ArrayList<SocketAddress> destinations; 
	private String message;
	private int port;
	private DatagramSocket broadcaster;

	public Broadcaster(int port) throws SocketException {
		this.destinations = new ArrayList<SocketAddress>();
		this.port = port;
		broadcaster = new DatagramSocket(port);
	}
	
	public void setDestinations(ArrayList<SocketAddress> destinations) {
		this.destinations = destinations;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		/*Add check if already running
		 * Deal with overlapping calls*/
		for(SocketAddress sock : destinations) {
			byte[] sendData = message.getBytes();
			System.out.println("Sending data to: " + sock);
			DatagramPacket sendPacket =
				new DatagramPacket(sendData, sendData.length, sock);
			try {
				broadcaster.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
