package pl.edu.pk.iti.copperAnt.simulation;

public interface Event {

	long getTime();

	void run(Clock clock);

}
