package pl.edu.pk.iti.copperAnt.simulation.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.network.Computer;
import pl.edu.pk.iti.copperAnt.network.Package;
import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class ARPResolveEvent extends ComputerSendsEvent {
	private static final Logger log = LoggerFactory
			.getLogger(ARPResolveEvent.class);

	private Event eventAfter;

	public ARPResolveEvent(long time, Computer device, Package pack,
			Event eventAfter) {
		super(time, device, pack);
		this.eventAfter = eventAfter;

	}

	public Package getPackage() {
		return this.pack;
	}

	@Override
	public void run(Clock clock) {
		if (!this.computer.knownHost(this.pack.getContent())) {
			clock.addEvent(new PortSendsEvent(time, this.computer.getPort(),
					pack));
		} else if (this.eventAfter != null) {
			eventAfter.getPackage().setDestinationMAC(
					this.computer.getKnownHost(this.pack.getContent()));

			clock.addEvent(eventAfter);
		}
		log.info(this.toString());

	}

	@Override
	public String toString() {
		return super.toString() + "ARPResolveEvent [computer=" + this.computer
				+ "]";
	}

}