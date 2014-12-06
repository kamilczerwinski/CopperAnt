package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.gui.PortControl;
import pl.edu.pk.iti.copperAnt.gui.RouterControl;
import pl.edu.pk.iti.copperAnt.gui.WithControl;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.ComputerSendsEvent;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Router implements Device, WithControl {
	private static final Logger log = LoggerFactory
			.getLogger(ComputerSendsEvent.class);
	private final List<Port> ports;
	private List<IPAddress> ipAddresses;
	private List<IPAddress> dhcpAddressList;
	
	private HashMap<Port, IPAddress> portIP;
	private Clock clock;
	private HashMap<String, Port> routingTable; // <IP, Port>

	private Properties config;
	private RouterControl control;

	public Router(int numberOfPorts, Clock clock) {
		this(numberOfPorts, clock, false);
	}

	public Router(int numberOfPorts, Clock clock, boolean withGui) {
		this.clock = clock;
		ports = new ArrayList<Port>(numberOfPorts);
		dhcpAddressList = new ArrayList<IPAddress>(numberOfPorts);
		Random generator = new Random();
	portIP = new HashMap<Port, IPAddress>();
		ipAddresses = new ArrayList<IPAddress>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this, withGui));
			IPAddress tmp = new IPAddress("192.168.0.1");
			tmp.set(3, generator.nextInt(254) + 1);
			ipAddresses.add(tmp);
			dhcpAddressList.add(new IPAddress(tmp));
			
		}
		routingTable = new HashMap<String, Port>();
		config = new Properties();
		
		if (withGui) {
			List<PortControl> list = new ArrayList<PortControl>(numberOfPorts);
			for (Port port : ports) {
				list.add(port.getControl());
			}
			control = new RouterControl(list);
		}
	}

	public Router(Properties config, Clock clock) {
		this.clock = clock;
		int numberOfPorts = Integer.parseInt((String) config
				.get("numbersOfPorts"));
		this.config = config;
		Random generator = new Random();

		String withGuiString = (String) config.getProperty("withGui", "false");
		boolean withGui = withGuiString.equals("true") ? true : false;
		ports = new ArrayList<Port>(numberOfPorts);
		dhcpAddressList = new ArrayList<IPAddress>(numberOfPorts);

		ipAddresses = new ArrayList<IPAddress>(numberOfPorts);

		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this, withGui));
			IPAddress tmp = new IPAddress("192.168.0.1");
			tmp.set(3, generator.nextInt(254) + 1);
			ipAddresses.add(tmp);
			dhcpAddressList.add(new IPAddress(tmp));

		}
		routingTable = new HashMap<String, Port>();
		

		
		if (withGui) {
			List<PortControl> list = new ArrayList<PortControl>(numberOfPorts);
			for (Port port : ports) {
				list.add(port.getControl());
			}
			control = new RouterControl(list);
		}
	}

	

	private String generateIP(int index) {
		return dhcpAddressList.get(index).increment();

	}

	public Port getPort(int portNumber) {
		return ports.get(portNumber);
	}
	public String getIP(int portNumber) {
		return ipAddresses.get(portNumber).toString();
	}
	public String getIP(Port port) {
		return ipAddresses.get(ports.indexOf(port)).toString();
	}

	private boolean isMyIP(String addr) {
		for (IPAddress ip: ipAddresses) {
			
			if (ip.toString().equals(addr)) {
				return true;
			}
		}
		return false;
	}

	// to facilitate unit testing
	public void addRouting(String ip, Port port) {
		routingTable.put(ip, port);

	}

	@Override
	public void acceptPackage(Package pack, Port inPort) {
		log.debug("Accept pacakge from " + pack.getSourceIP() + " to "
				+ pack.getSourceIP());
		String destinationIP = pack.getDestinationIP();
		String sourceIP = pack.getSourceIP();
		Port outPort = null;
		Package response = pack;
		if (!pack.validTTL()) {
			log.debug("Pack has not valid ttl!");
			response = new Package(PackageType.DESTINATION_UNREACHABLE, "TTL<0");
			response.setDestinationIP(sourceIP);
			response.setDestinationMAC(pack.getDestinationMAC());
			outPort = inPort;

		}
		if (pack.getType() == PackageType.DHCP) {
			// request to this router to get IP, DHCP only local network
			if (pack.getContent() == null) {
				log.debug("Request to router for IP");
				sourceIP = generateIP(ports.indexOf(inPort));
				response = new Package(PackageType.DHCP, sourceIP);
				response.setDestinationMAC(pack.getDestinationMAC());
				outPort = inPort;
			} else {
				// response from wan router
				// FIXME: what happen when we connect two routers in DHCP mode?
				log.debug("Get WAN ip");

				return;
			}

		} else if (pack.getType() == PackageType.ECHO_REQUEST
				&& this.isMyIP(destinationIP)) {
			log.debug("Response for ECHO_REQUEST");
			response = new Package(PackageType.ECHO_REPLY, pack.getContent());
			response.setDestinationMAC(pack.getSourceMAC());
			response.setDestinationIP(sourceIP);
			outPort = inPort;
		}
		if (!routingTable.containsKey(sourceIP)) {
			log.debug("Adding source ip " + sourceIP + " to routingTable");
			routingTable.put(sourceIP, inPort);
		}

		if (routingTable.containsKey(destinationIP)
				&& !this.isMyIP(destinationIP)) {
			// IP in table
			log.debug("Know IP, send to LAN port");

			outPort = routingTable.get(destinationIP);

		} else if (outPort == null) {
			// routing
			boolean isInSubnet = false;
			for (int i = 0, len = ipAddresses.size(); i < len; ++i) {
				if (ipAddresses.get(i).isInRange(destinationIP)) {
					outPort = this.getPort(i);
					isInSubnet = true;
					break;
				}
			}
			if (!isInSubnet) {
				for (Port port : ports) {
					if (port != inPort) {						
						clock.addEvent(new PortSendsEvent(clock.getCurrentTime()				
							+ getDelay(), port, pack));
					}
				}
				return;
			}
			
			
		}
		

		clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + getDelay(),
				outPort, response));

	}

	
	public String getMac() {
		return this.ports.get(0).getMAC();
	}

	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}
	

	public RouterControl getControl() {
		return control;
	}

	public void setControl(RouterControl control) {
		this.control = control;
	}

}
