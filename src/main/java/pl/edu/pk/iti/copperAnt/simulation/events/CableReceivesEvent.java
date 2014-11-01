package pl.edu.pk.iti.copperAnt.simulation.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.network.Cable;
import pl.edu.pk.iti.copperAnt.network.CableState;
import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class CableReceivesEvent extends Event {
	private static final Logger log = LoggerFactory
			.getLogger(CableReceivesEvent.class);

	private Cable cable;
	private Port port;

	public CableReceivesEvent(long time, Port fromPort) {
		super(time);
		cable = fromPort.getCable();
		port = fromPort;
	}

	@Override
	public void run(Clock clock) {
		cable.setBusyUntil(time + cable.getDelay());
		if (cable.getState() == CableState.IDLE) {
			cable.setState(CableState.BUSY);
			clock.addEvent(new CableSendsEvent(time + cable.getDelay(), cable
					.getOtherEnd(port)));
		} else {
			cable.setState(CableState.COLISION);
			log.debug("There was collision. Package was lost.");
		}
		log.debug(this.toString());
	}

	@Override
	public String toString() {
		return super.toString() + "CableReceivesEvent [cable=" + cable
				+ ", port=" + port + "]";
	}
}
