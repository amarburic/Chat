package transmission;
import java.net.SocketException;
import java.util.ArrayList;

import model.Message;
import statics.Values;
import view.GUI;

public class ChatServer {

	private ArrayList<Message> receivedMessages;
	private Connector connector; 
	private Receiver receiver; 
	private GUI gui;
	
	public ChatServer() throws SocketException {
		receivedMessages = new ArrayList<Message>();
		connector = new Connector(Values.SERVER_CONNECT_PORT, this);
		receiver = new Receiver(Values.SERVER_RECEIVE_PORT, this);
		gui = new GUI("Chat Server", this);
	}
	
	public void run() {
		(new Thread(connector)).start();
		(new Thread(receiver)).start();
		gui.showGui();
		
	}
	
	public ArrayList<Message> getReceivedMessages() {
		return receivedMessages;
	}
	
	public ArrayList<Message> getMessagesDelta(long time) {
		ArrayList<Message> messages = new ArrayList<Message>();
		for(int i = receivedMessages.size() - 1; i >= 0; i--) {
			Message m = receivedMessages.get(i);
			if(m.getTimeOfArrival() > time)
				messages.add(m);
			else 
				break;
		}
		ArrayList<Message> messagesReverse = new ArrayList<Message>();
		for(int i = messages.size() - 1; i >= 0; i--)
			messagesReverse.add(messages.get(i));
		return messagesReverse;
	}
	
	public void updateMessageList(ArrayList<Message> messages) {
		receivedMessages.addAll(messages);
		gui.updateMessages(messages);
	}
	
	public void updateMessageList(Message m) {
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(m);
		updateMessageList(messages);
	}
	
	public static void main(String[] args) throws Exception {
		(new ChatServer()).run();
		
	}

}


