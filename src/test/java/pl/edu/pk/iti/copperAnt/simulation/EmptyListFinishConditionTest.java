package pl.edu.pk.iti.copperAnt.simulation;

import org.junit.Assert;
import org.junit.Test;

import pl.edu.pk.iti.copperAnt.simulation.events.SimpleMockEvent;

public class EmptyListFinishConditionTest {

	@Test
	public void testIsSatisfied() {
		// given
		Clock clock = new Clock();
		clock.setFinishCondition(new EmptyListFinishCondition());
		clock.addEvent(new SimpleMockEvent(10));
		// when
		clock.run();
		// then
		Assert.assertTrue(clock.events.isEmpty());
	}

}
