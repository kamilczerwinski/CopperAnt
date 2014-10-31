package pl.edu.pk.iti.copperAnt.simulation.events;

import pl.edu.pk.iti.copperAnt.network.Cable;
import pl.edu.pk.iti.copperAnt.network.CableState;
import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class CableReveivesEvent extends Event {

	private Cable cable;
	private Port port;

	public CableReveivesEvent(long time, Port fromPort) {
		super(time);
		cable = fromPort.getCable();
		port = fromPort;
	}

	@Override
	public void run(Clock clock) {
		if (cable.getState() == CableState.IDLE) {
			cable.setState(CableState.BUSY);
			clock.addEvent(new CableSendsEvent(time + getCabelDelay(), cable
					.getOtherEnd(port)));
		} else {
			cable.setState(CableState.COLISION);
		}
	}

	private long getCabelDelay() {
		// TODO uzaleznić to od długości kabla
		return 1;
	}

}
