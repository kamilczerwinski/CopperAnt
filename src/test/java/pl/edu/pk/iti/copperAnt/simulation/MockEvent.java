package pl.edu.pk.iti.copperAnt.simulation;

import pl.edu.pk.iti.copperAnt.simulation.Event;

public class MockEvent implements Event {

	private long time;

	@Override
	public long getTime() {
		return this.time;
	}

	public MockEvent withTime(long time) {
		this.time = time;
		return this;
	}

}
