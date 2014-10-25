package pl.edu.pk.iti.copperAnt.simulation;

public class SimpleMockEvent implements Event {

	private long time;

	@Override
	public long getTime() {
		return this.time;
	}

	public SimpleMockEvent withTime(long time) {
		this.time = time;
		return this;
	}

	@Override
	public void run(Clock clock) {
		System.out.println(this);

	}

	@Override
	public String toString() {
		return "SimpleMockEvent [time=" + time + "]";
	}

}
