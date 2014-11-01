package pl.edu.pk.iti.copperAnt.network;

import java.util.ArrayList;
import java.util.List;

public class Hub implements Device {
	private final List<Port> ports;

	public Hub(int numberOfPorts) {
		ports = new ArrayList<Port>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			ports.add(new Port());
		}
	}

	public Port getPort(int portNumber) {
		return ports.get(portNumber);
	}

	@Override
	public void acceptPackage(Package pack) {

	}

}
