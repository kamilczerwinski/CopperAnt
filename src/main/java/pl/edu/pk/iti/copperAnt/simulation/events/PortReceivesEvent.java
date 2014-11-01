package pl.edu.pk.iti.copperAnt.simulation.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.network.Package;
import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class PortReceivesEvent extends Event {
	private static final Logger log = LoggerFactory
			.getLogger(PortReceivesEvent.class);

	private static final long timeOfProcessing = 1;

	private Port port;

	public PortReceivesEvent(long time, Port port, Package pack) {
		super(time);
		this.port = port;

	}

	@Override
	public void run(Clock clock) {
		log.debug(this.toString());

	}

	@Override
	public String toString() {
		return super.toString() + "PortReceivesEvent [port=" + port + "]";
	}

}
