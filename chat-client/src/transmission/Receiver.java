package transmission;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import model.Message;
import statics.Helpers;
import statics.Values;

public class Receiver implements Runnable {

	private DatagramSocket listenerSocket = null;
	private ChatClient cc;
	private InetAddress serverIp; 
	private int serverPort;
	
	private final int DELAY = 250;
	
	public Receiver(InetAddress serverIp, int serverPort, ChatClient cc)
			throws SocketException {
		this.cc = cc;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		listenerSocket = new DatagramSocket();
	}
	
	public int getPort() {
		return listenerSocket.getPort();
	}
	
	public void requestMessages() {
		byte[] sendData = (cc.getLastMessageTime() + "").getBytes();
		DatagramPacket sendPacket =
			new DatagramPacket(sendData, sendData.length, serverIp, serverPort);
		try {
			listenerSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			requestMessages();
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			boolean received = true;
			try {
				listenerSocket.setSoTimeout(DELAY);
				listenerSocket.receive(receivePacket);
			} catch (SocketTimeoutException ste) {
				received = false;
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(received) {
				if(!(new String(receivePacket.getData()).trim().equals(Values.noMessages))) {
					ArrayList<Message> messages;
					try {
						messages = Helpers.<ArrayList<Message>>convertFromBytes(receivePacket.getData());
						cc.updateMessageList(messages);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

}
