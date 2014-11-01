package pl.edu.pk.iti.copperAnt.simulation.events;

import pl.edu.pk.iti.copperAnt.network.Computer;
import pl.edu.pk.iti.copperAnt.network.Package;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.TimeIntervalGenerator;

public class CoputerSendsEvent extends Event {
	private static final long DELAY = 1;
	private Computer computer;
	private Package pack;
	// TODO tu bedzie zastosowana inna implementacja
	TimeIntervalGenerator intervalGenerator = new ConstantTimeIntervalGenerator(
			100);

	public CoputerSendsEvent(long time, Computer computer, Package pack) {
		super(time);
		this.computer = computer;
		this.pack = pack;
	}

	@Override
	public void run(Clock clock) {
		long timeToNextEvent = intervalGenerator.getTimeInterval();
		PortSendsEvent event = new PortSendsEvent(time + DELAY,
				computer.getPort(), pack);

		CoputerSendsEvent nextComputerSendsEvent = new CoputerSendsEvent(time
				+ timeToNextEvent, computer, pack)
				.withIntervalGenerator(this.intervalGenerator);
		clock.addEvent(event);
		clock.addEvent(nextComputerSendsEvent);

	}

	public TimeIntervalGenerator getIntervalGenerator() {
		return intervalGenerator;
	}

	public void setIntervalGenerator(TimeIntervalGenerator intervalGenerator) {
		this.intervalGenerator = intervalGenerator;
	}

	public CoputerSendsEvent withIntervalGenerator(
			TimeIntervalGenerator generator) {
		setIntervalGenerator(generator);
		return this;
	}

}
