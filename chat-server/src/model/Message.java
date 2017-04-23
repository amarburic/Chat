package model;

import java.io.Serializable;
import java.net.InetAddress;

public class Message implements Serializable {
	
	long timeOfArrival;
	String content;
	InetAddress senderIp;
	int senderPort;
	
	public Message(String content, InetAddress senderIp, int senderPort, long timeOfArrival) {
		this.content = content;
		this.senderIp = senderIp;
		this.senderPort = senderPort;
		this.timeOfArrival = timeOfArrival;
	}
	
	public Message(String content, InetAddress senderIp, int senderPort) {
		this(content, senderIp, senderPort, System.currentTimeMillis());
	}
	
	@Override
	public String toString() {
		return senderIp + ": " + content;
	}
	public long getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(long timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public InetAddress getSenderIp() {
		return senderIp;
	}

	public void setSenderIp(InetAddress senderIp) {
		this.senderIp = senderIp;
	}

	public int getSenderPort() {
		return senderPort;
	}

	public void setSenderPort(int senderPort) {
		this.senderPort = senderPort;
	}
}
