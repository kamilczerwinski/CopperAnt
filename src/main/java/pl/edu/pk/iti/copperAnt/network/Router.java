package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Router implements Device {
	private static final long DELAY = 1;
	private final List<Port> ports;
	private Clock clock;
    private HashMap<String, Port> routingTable;   // <IP, Port>
	private String MAC;
	private String ip;

	public Router(int numberOfPorts, Clock clock) {
		this.clock = clock;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
		routingTable = new HashMap<String, Port>();
	}

	public Port getPort(int portNumber) {
		return ports.get(portNumber);
	}
	
	// to facilitate unit testing
	public void addRouting(String ip, Port port) {
    	routingTable.put(ip, port);

	}
	
	@Override
	public void acceptPackage(Package pack, Port inPort) {
		String destinationIP = pack.getDestinationIP();
	    String sourceIP = pack.getSourceIP();
	   
	  
	    if (!routingTable.containsKey(sourceIP))  {
	    	routingTable.put(sourceIP, inPort);
	    }
	    // NAT?
	    pack.setSourceIP(this.getIP());
		
	    if (routingTable.containsKey(destinationIP)) {
	    	// IP in table
	    	 clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
	    			routingTable.get(destinationIP), pack));
	    } else {
	    	// Destination Host Unreachable
	   	 clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
	    			inPort, pack));
	    	
	    }
		

	}
	
	public String getIP() {
		return ip;
	}
	public String getMac() {
		return MAC;
	}
	


	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

}
