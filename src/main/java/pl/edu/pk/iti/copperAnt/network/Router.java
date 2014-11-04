package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Router implements Device {
	private static final long DELAY = 1;
	private final List<Port> ports;
	private Clock clock;
	private String m_Mac;
	private String m_ip;

	public Router(int numberOfPorts, Clock clock) {
		this.clock = clock;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
	}

	public Port getPort(int portNumber) {
		return ports.get(portNumber);
	}
	//FIXME: port zrodlowy by sie tez przydał
	@Override
	public void acceptPackage(Package pack) {
		Port finded = null;
		for (Port port : ports) {
			// wysylanie do PC lub huba/switcha
			if (port.getDevice().getIp() == pack.getToIp() || port.getDevice().getMac() == pack.getToMac()) {
				clock.addEvent(new PortSendsEvent(clock.getCurrentTime() + DELAY,
						port, pack));
				return;
			} 
			
		}
		// TODO: odeslanie odpowiedzi na port zrodlow ze host nie znaleziony
	

	}
	
	public String getIp() {
		return m_ip;
	}
	public String getMac() {
		return m_Mac;
	}
	


}
