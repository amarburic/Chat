import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Receiver implements Runnable {

	private DatagramSocket listenerSocket = null;
	private ChatClient cc;
	
	public Receiver(DatagramSocket socket, ChatClient cc)
			throws SocketException {
		this.cc = cc;
		listenerSocket = socket;
	}
	
	public int getPort() {
		return listenerSocket.getPort();
	}

	@Override
	public void run() {
		while (true) {
			System.out.println("Receiving-client");
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = new String( receivePacket.getData()).trim();
			cc.updateMessageList(message);
		}
	}

}
