package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Router implements Device {
	private static final long DELAY = 1;
	private final List<Port> ports;
	private final Port wanPort = new Port(this);
	private Clock clock;
    private HashMap<String, Port> routingTable;   // <IP, Port>
	private String MAC;
	private IPAddress wanIP;
	private IPAddress lanIP = new IPAddress("192.168.0.1");
	private Properties config;

	public Router(int numberOfPorts, Clock clock) {
		this.clock = clock;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
		routingTable = new HashMap<String, Port>();
		config = new Properties();
		Random generator = new Random(); 
		lanIP.set(3, generator.nextInt(254) + 2);
		
	}

	public Router(Properties config, Clock clock) {
		this.clock = clock;
		int numberOfPorts = Integer.parseInt((String)config.get("numbersOfPorts"));
		this.config = config;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
		routingTable = new HashMap<String, Port>();
		String startIP = (String)config.get("DHCPstartIP");
		

		if (!startIP.isEmpty()) {
			lanIP = new IPAddress(startIP);
		}
			

	}
	
	public void init() {
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.DHCP, null);
		clock.addEvent(new PortSendsEvent(time, this.wanPort, pack));
		
	}
	
	private String generateIP() {
		return lanIP.increment();

	}
	public Port getPort(int portNumber) {
		return ports.get(portNumber);
	}
	public Port getWanPort() {
		return wanPort;
		
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
	    Package response = pack;
	    if (!pack.validTTL()) {
	    	response = new Package(PackageType.DESTINATION_UNREACHABLE, "TTL<0");
	    	response.setDestinationIP(sourceIP);
	    	response.setDestinationMAC(pack.getDestinationMAC());
	    	outPort = inPort;
	    	
	    } 
	    if (pack.getType() == PackageType.DHCP) {
	    	// request to this router to get IP,  DHCP only local network
	    	if (pack.getContent() == null && inPort != this.getWanPort()) {
	    		sourceIP = generateIP();
	    		response = new Package(PackageType.DHCP, sourceIP);
	    		response.setDestinationMAC(pack.getDestinationMAC());
	    		outPort = inPort;
	    	} else {
	    		// response from wan router
	    		this.wanIP = new IPAddress(pack.getContent());
	    		return;
	    	}
	    	
  	    	
	    } else if (pack.getType() == PackageType.ECHO_REQUEST && destinationIP == this.getIP()) {
	    	response = new Package(PackageType.ECHO_REPLY, pack.getContent());
	    	response.setDestinationMAC(pack.getSourceMAC());
	    	response.setSourceIP(pack.getSourceIP());
	    	outPort = inPort;
	    }
	     if (!routingTable.containsKey(sourceIP))  {
	    	routingTable.put(sourceIP, inPort);
	    }
	   
		
	    if (routingTable.containsKey(destinationIP) && destinationIP != this.getIP()) {
	    	// IP in table
	    	outPort = routingTable.get(destinationIP);
	    	
	    } else if (outPort == null) {
	    	// Destination Host Unreachable in router network send to wan router
	   	 	outPort = this.getWanPort();
	   	 	response = pack;
	    	
	    }

	    clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
    			outPort, response));

	}
	
	public String getIP() {
		return lanIP.toString();
	}
	public String getWanIP() {
		return wanIP.toString();
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
