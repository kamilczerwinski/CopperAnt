package pl.edu.pk.iti.copperAnt.simulation.events;

import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class ComplexMockEvent extends Event {

	public ComplexMockEvent(long time) {
		super(time);
	}

	public ComplexMockEvent withTime(long time) {
		this.time = time;
		return this;
	}

	@Override
	public void run(Clock clock) {
		clock.addEvent(new SimpleMockEvent(this.time + 10));
		clock.addEvent(new SimpleMockEvent(this.time + 100));
		System.out.println(this);

	}

	@Override
	public String toString() {
		return "ComplexMockEvent [time=" + time + "]";
	}

}
