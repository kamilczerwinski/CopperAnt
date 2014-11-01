package pl.edu.pk.iti.copperAnt.simulation.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.TimeIntervalGenerator;

public class PortSendsEvent extends Event {
	private static final Logger log = LoggerFactory
			.getLogger(PortSendsEvent.class);

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
		log.debug(this.toString());
		long timeOfNextEvent = intervalGenerator.getTimeInterval();
		clock.addEvent(new PortSendsEvent(this.time + timeOfNextEvent,
				this.port).withIntervalGenerator(this.intervalGenerator));
		clock.addEvent(new CableReceivesEvent(this.time + timeOfProcessing,
				port));

	}

	@Override
	public String toString() {
		return super.toString() + "PortSendsEvent [port=" + port + "]";
	}

	public TimeIntervalGenerator getIntervalGenerator() {
		return intervalGenerator;
	}

	public void setIntervalGenerator(TimeIntervalGenerator intervalGenerator) {
		this.intervalGenerator = intervalGenerator;
	}

	public PortSendsEvent withIntervalGenerator(TimeIntervalGenerator generator) {
		setIntervalGenerator(generator);
		return this;
	}
}
