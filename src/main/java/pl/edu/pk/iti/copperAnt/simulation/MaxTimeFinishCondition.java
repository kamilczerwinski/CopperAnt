package pl.edu.pk.iti.copperAnt.simulation;

import pl.edu.pk.iti.copperAnt.simulation.events.Event;

public class MaxTimeFinishCondition implements FinishCondition {

	private long maxTime;

	public MaxTimeFinishCondition(long maxTime) {
		this.maxTime = maxTime;

	}

	@Override
	public boolean isSatisfied(Clock clock) {
		if (!clock.events.isEmpty()) {
			Event firstEvent = clock.events.get(0);
			return firstEvent == null || firstEvent.getTime() > maxTime;
		} else {
			return true;
		}
	}
}
