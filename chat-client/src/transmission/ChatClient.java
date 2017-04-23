package transmission;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.Message;
import statics.Values;
import view.GUI;

public class ChatClient {

	private ArrayList<Message> receivedMessages;
	private Receiver receiver;
	private Sender sender;
	private GUI gui;
	
	public ChatClient() throws SocketException, UnknownHostException {
		receivedMessages = new ArrayList<Message>();
		receiver = new Receiver(InetAddress.getByName(Values.SERVER_IP), Values.SERVER_CONNECT_PORT, this);
		sender = new Sender(Values.SERVER_RECEIVE_PORT, 
				InetAddress.getByName(Values.SERVER_IP));
		gui = new GUI("Chat Client", this);
	}
	
	public void run() throws UnknownHostException {
		//(new Timer()).schedule(connector, 0, updatePeriod);
		gui.showGui();
		(new Thread(receiver)).start();
		
	}
	
	public long getLastMessageTime() {
		if(receivedMessages.size() == 0) {
			return 0;
		} else {
			return receivedMessages.get(receivedMessages.size() - 1).getTimeOfArrival();
		}
	}
	
	public void updateMessageList(ArrayList<Message> messages) {
		receivedMessages.addAll(messages);
		gui.updateMessages(messages);
	}
	
	public void sendMessage(String message) {
		sender.setMessage(message);
		(new Thread(sender)).start();
	}
	
	public static void main(String args[]) throws Exception {
		(new ChatClient()).run();
	}
}
