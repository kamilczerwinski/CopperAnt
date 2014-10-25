package pl.edu.pk.iti.copperAnt.simulation;

public class ComplexMockEvent implements Event {

	private long time;

	@Override
	public long getTime() {
		return this.time;
	}

	public ComplexMockEvent withTime(long time) {
		this.time = time;
		return this;
	}

	@Override
	public void run(Clock clock) {
		clock.addEvent(new SimpleMockEvent().withTime(this.time + 10));
		clock.addEvent(new SimpleMockEvent().withTime(this.time + 100));
		System.out.println(this);

	}

	@Override
	public String toString() {
		return "ComplexMockEvent [time=" + time + "]";
	}

}
