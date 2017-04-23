package transmission;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Message;
import statics.Helpers;
import statics.Values;

public class Connector implements Runnable {
	
	private HashMap<SocketAddress, Long> connectedUsers;
	private int port;
	private DatagramSocket listenerSocket = null;
	//private Updater updater;
	ChatServer cs;
	
	private final int updatePeriod = 100000000;
	/*
	public Map<SocketAddress, Long> getConnectedUsers() {
		return connectedUsers;
	}
	*/
	public Connector(int port, ChatServer cs) throws SocketException {
		connectedUsers = new HashMap<SocketAddress, Long>();
		//updater = new Updater(connectedUsers, updatePeriod);
		this.port = port;
		this.cs = cs;
		listenerSocket = new DatagramSocket(port);
	}
	
	@Override
	public void run() {
		//(new Timer()).schedule(updater, 0, updatePeriod);
		byte[] receiveData = new byte[1024];
		while(true) {
			//System.out.println("Listening for new connections");
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//InetAddress connectedIp = receivePacket.getAddress();
			//int connectedPort = Integer.parseInt((new String(receivePacket.getData())).trim());
			InetSocketAddress socketAddress = (InetSocketAddress)receivePacket.getSocketAddress();
			String ip = receivePacket.getAddress().getHostAddress();
			String msg = (new String(receivePacket.getData()).trim());
			//System.out.println("Received in connector: " + msg + " Size: " + msg.length());
			//System.out.println("Received in connector: " + msg + " Size: " + msg.length());
			Long time = Long.valueOf(msg);
			//System.out.println("Converted in connector to: " + time);
			ArrayList<Message> messagesToSend = cs.getMessagesDelta(time);
			
			if(messagesToSend.size() == 0) {
				byte[] sendData = Values.noMessages.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
						socketAddress.getAddress(), socketAddress.getPort());
				try {
					listenerSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					byte[] sendData = Helpers.<ArrayList<Message>>convertToBytes(messagesToSend);
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
							socketAddress.getAddress(), socketAddress.getPort());
					listenerSocket.send(sendPacket);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println("Sent packet to: " + socketAddress);
			/*
			try {
			
			boolean found = false;
			for(Map.Entry<SocketAddress, Long> user : connectedUsers.entrySet()) {
				String storedIp = ((InetSocketAddress)(user.getKey())).getAddress().getHostAddress();
				//System.out.println("Stored ip: " + storedIp);
				if(storedIp.equals(ip)) {
					user.setValue(System.currentTimeMillis());
					found = true;
					break;
				}
			}
			if(!found) {
				System.out.println(socketAddress + " connected");
				connectedUsers.put(socketAddress, System.currentTimeMillis());
				while(true) {
					String confirmMessage = "You are now connected";
					byte[] sendData = confirmMessage.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
							socketAddress.getAddress(), socketAddress.getPort());
					try {
						listenerSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Sent packet to: " + socketAddress);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/
		}
	}

}
