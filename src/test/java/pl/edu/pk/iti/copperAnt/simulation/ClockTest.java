package pl.edu.pk.iti.copperAnt.simulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.edu.pk.iti.copperAnt.simulation.Clock;

public class ClockTest {

	@Test
	public void testAddEvent() {
		// given
		Clock clock = new Clock();
		// when
		clock.addEvent(new MockEvent().withTime(6));
		clock.addEvent(new MockEvent().withTime(3));
		clock.addEvent(new MockEvent().withTime(1));
		clock.addEvent(new MockEvent().withTime(2));
		clock.addEvent(new MockEvent().withTime(2));
		clock.addEvent(new MockEvent().withTime(4));
		// then
		assertEquals(clock.getNumberOfWaitingEvent(), 6);
		assertEquals(clock.getEventFromList(0).getTime(), 1);
		assertEquals(clock.getEventFromList(1).getTime(), 2);
		assertEquals(clock.getEventFromList(2).getTime(), 2);
		assertEquals(clock.getEventFromList(3).getTime(), 3);
		assertEquals(clock.getEventFromList(4).getTime(), 4);
		assertEquals(clock.getEventFromList(5).getTime(), 6);

	}

}
