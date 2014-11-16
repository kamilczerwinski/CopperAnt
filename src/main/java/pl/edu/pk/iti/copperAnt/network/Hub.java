package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Hub implements Device {
	private final List<Port> ports;
	private Clock clock;

	public Hub(int numberOfPorts, Clock clock) {
		this.clock = clock;
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port(this));
		}
	}

	public Port getPort(int portNumber) {
		return ports.get(portNumber);
	}

	@Override
	public void acceptPackage(Package pack, Port inPort) {
		for (Port port : ports) {
			clock.addEvent(new PortSendsEvent(clock.getCurrentTime()
					+ getDelay(), port, pack));
		}
	}

	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}
}
