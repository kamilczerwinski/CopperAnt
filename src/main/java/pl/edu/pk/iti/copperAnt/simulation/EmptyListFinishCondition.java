package pl.edu.pk.iti.copperAnt.simulation;

public class EmptyListFinishCondition implements FinishCondition {

	@Override
	public boolean isSatisfied(Clock clock) {
		return clock.events.isEmpty();
	}
}
