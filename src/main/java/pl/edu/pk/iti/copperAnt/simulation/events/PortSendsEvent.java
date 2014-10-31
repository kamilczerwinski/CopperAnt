package pl.edu.pk.iti.copperAnt.simulation.events;

import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.TimeIntervalGenerator;

public class PortSendsEvent extends Event {

	private static final long timeOfProcessing = 1;
	// TODO tu bedzie zastosowana inna implementacja
	TimeIntervalGenerator intervalGenerator = new ConstantTimeIntervalGenerator(
			100);

	private Port port;

	public PortSendsEvent(long time, Port port) {
		super(time);
		this.port = port;

	}

	@Override
	public void run(Clock clock) {

		long timeOfNextEvent = intervalGenerator.getTimeInterval();
		clock.addEvent(new PortSendsEvent(this.time + timeOfNextEvent,
				this.port));
		clock.addEvent(new CableReveivesEvent(this.time + timeOfProcessing,
				port));

	}

}
