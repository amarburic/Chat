package transmission;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

public class Updater extends TimerTask {

	private Map<SocketAddress, Long> connectedUsers;
	private long timeLimit;
	
	public Updater(Map<SocketAddress, Long> connectedUsers, long timeLimit) {
		this.connectedUsers = connectedUsers;
		this.timeLimit = timeLimit;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		Iterator it = connectedUsers.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<SocketAddress, Long> entry = (Map.Entry<SocketAddress, Long>)it.next();
	        if(entry.getValue() < currentTime - timeLimit) {
	        	System.out.println(entry.getKey() + " disconnected");
	        	it.remove();
	        }
	    }
	}

}
