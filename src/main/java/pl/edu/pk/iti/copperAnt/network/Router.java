package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Router implements Device {
	private static final long DELAY = 1;
	private final List<Port> ports;
	private Clock clock;
    private HashMap<String, Port> routingTable;   // <IP, Port>
	private String MAC;
	private String ip;
	private Properties config;
	private String[] startIPparts;

	public Router(int numberOfPorts, Clock clock) {
		this.clock = clock;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
		routingTable = new HashMap<String, Port>();
	}

	public Router(Properties config, Clock clock) {
		this.clock = clock;
		int numberOfPorts = (int)config.get("numbersOfPorts");
		this.config = config;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
		routingTable = new HashMap<String, Port>();
		String startIP = (String)config.get("DHCPstartIP");
		startIPparts = startIP.split("."); // only IPv4

	}
	
	private String generateIP() {
		startIPparts[startIPparts.length - 1] = Integer.toString(Integer.parseInt(startIPparts[startIPparts.length - 1]) + 1);
	    StringBuilder ip = new StringBuilder();

		for (int i = 0, len = startIPparts.length; i < len; ++i) {
			ip.append(startIPparts[i] + ".");
		}
		return new String(ip.deleteCharAt(ip.length() - 1));

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
	    Port outPort = null;
	    Package response = null;
	    if (!pack.validTTL()) {
	    	response = new Package(PackageType.DESTINATION_UNREACHABLE, "TTL<0");
	    	response.setDestinationIP(sourceIP);
	    	outPort = inPort;
	    	
	    } 
	    if (pack.getType() == PackageType.DHCP) {
	    	sourceIP = generateIP();
	    	response = new Package(PackageType.DHCP, sourceIP);
	    	response.setDestinationMAC(pack.getDestinationMAC());
	    	outPort = inPort;
	    	
  	    	
	    }
	     if (!routingTable.containsKey(sourceIP))  {
	    	routingTable.put(sourceIP, inPort);
	    }
	    // NAT?
	    pack.setSourceIP(this.getIP());
		
	    if (routingTable.containsKey(destinationIP)) {
	    	// IP in table
	    	outPort = routingTable.get(destinationIP);
	    	
	    } else if (outPort == null) {
	    	// Destination Host Unreachable
	   	 	outPort = inPort;
	   	 	response = pack;
	   	 	response.setType(PackageType.DESTINATION_UNREACHABLE);
	    	
	    }
	    clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
    			outPort, response));

	}
	
	public String getIP() {
		return ip;
	}
	public String getMac() {
		return this.ports.get(0).getMAC();
	}
	


	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

}
