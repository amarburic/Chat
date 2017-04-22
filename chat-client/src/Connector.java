import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.TimerTask;


public class Connector extends TimerTask {

	private InetAddress serverIp;
	private int serverPort;
	private DatagramSocket sender;
	
	private String message = "I'm here";
	
	public Connector(DatagramSocket sender, int serverPort, InetAddress serverIp) throws SocketException {
		this.serverPort = serverPort;
		this.serverIp = serverIp;
		this.sender = sender;
	}

	public void setServerIp(InetAddress serverIp) {
		this.serverIp = serverIp;
	}
	
	@Override
	public void run() {
		byte[] sendData = message.getBytes();
		DatagramPacket sendPacket =
			new DatagramPacket(sendData, sendData.length, serverIp, serverPort);
		try {
			sender.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
