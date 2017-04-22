import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {
	public static boolean hasMessage = false;
	public static String message;
	
	private ArrayList<String> receivedMessages;
	private Connector connector;
	private Receiver receiver;
	private Sender sender;
	private DatagramSocket socket;
	private GUI gui;
	
	private final int updatePeriod = 500;
	
	public ChatClient() throws SocketException, UnknownHostException {
		receivedMessages = new ArrayList<String>();
		socket = new DatagramSocket();
		receiver = new Receiver(socket, this);
		sender = new Sender(socket, Values.SERVER_RECEIVE_PORT, 
				InetAddress.getByName(Values.SERVER_IP));
		connector = new Connector(socket, Values.SERVER_CONNECT_PORT, 
				InetAddress.getByName(Values.SERVER_IP));
		gui = new GUI("Chat Client", this);
	}
	
	public void run() {
		(new Timer()).schedule(connector, 0, updatePeriod);
		(new Thread(receiver)).start();
		gui.showGui();
	}
	
	public void updateMessageList(String message) {
		receivedMessages.add(message);
		gui.updateMessages(receivedMessages);
	}
	
	public void sendMessage(String message) {
		sender.setMessage(message);
		(new Thread(sender)).start();
	}
	
	public static void main(String args[]) throws Exception {
		(new ChatClient()).run();
	}
}
