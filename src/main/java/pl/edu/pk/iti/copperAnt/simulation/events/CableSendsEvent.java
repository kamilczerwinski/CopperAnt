package pl.edu.pk.iti.copperAnt.simulation.events;

import pl.edu.pk.iti.copperAnt.network.Cable;
import pl.edu.pk.iti.copperAnt.network.CableState;
import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class CableSendsEvent extends Event {

	private Port port;
	private Cable cable;

	public CableSendsEvent(long time, Port port) {
		super(time);
		this.port = port;
		this.cable = port.getCable();

	}

	@Override
	public void run(Clock clock) {
		cable.setState(CableState.IDLE);

	}

}
