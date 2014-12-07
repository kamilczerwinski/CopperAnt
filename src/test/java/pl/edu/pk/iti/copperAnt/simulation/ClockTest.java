package pl.edu.pk.iti.copperAnt.simulation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.edu.pk.iti.copperAnt.simulation.events.Event;

public class ClockTest {

	@Test
	public void addEventSortsEventsTest() {
		// given
		Clock clock = new Clock();
		// when
		clock.addEvent(mockEventAtTime(6L));
		clock.addEvent(mockEventAtTime(3L));
		clock.addEvent(mockEventAtTime(1L));
		clock.addEvent(mockEventAtTime(2L));
		clock.addEvent(mockEventAtTime(2L));
		clock.addEvent(mockEventAtTime(4L));
		// then
		assertEquals(clock.getNumberOfWaitingEvent(), 6);
		assertEquals(clock.getEventFromList(0).getTime(), 1);
		assertEquals(clock.getEventFromList(1).getTime(), 2);
		assertEquals(clock.getEventFromList(2).getTime(), 2);
		assertEquals(clock.getEventFromList(3).getTime(), 3);
		assertEquals(clock.getEventFromList(4).getTime(), 4);
		assertEquals(clock.getEventFromList(5).getTime(), 6);

	}

	@Test
	public void runEventsInCorrectOrderTest() throws InterruptedException {
		// given
		Clock clock = new Clock();
		Event eventAt1 = mockEventAtTime(1L);
		Event eventAt2 = mockEventAtTime(2L);
		Event eventAt3 = mockEventAtTime(3L);
		Event eventAt4 = mockEventAtTime(4L);
		Event eventAt5 = mockEventAtTime(5L);
		Event eventAt6 = mockEventAtTime(6L);

		// when
		clock.addEvent(eventAt6);
		clock.addEvent(eventAt3);
		clock.addEvent(eventAt2);
		clock.addEvent(eventAt4);
		clock.addEvent(eventAt1);
		clock.addEvent(eventAt5);
		clock.run();

		// then

		InOrder inOrder = inOrder(eventAt1, eventAt2, eventAt3, eventAt4,
				eventAt5, eventAt6);

		inOrder.verify(eventAt1).run(clock);
		inOrder.verify(eventAt2).run(clock);
		inOrder.verify(eventAt3).run(clock);
		inOrder.verify(eventAt4).run(clock);
		inOrder.verify(eventAt5).run(clock);
		inOrder.verify(eventAt6).run(clock);

	}

	private Event mockEventAtTime(Long time) {
		Event eventAt1 = mock(Event.class);
		when(eventAt1.getTime()).thenReturn(time);
		return eventAt1;
	}

	@Test
	public void runEventsWithConsequencesTest() throws InterruptedException {
		// given
		Clock clock = new Clock();
		Event eventMock = mockEventAtTime(10L);
		Event nextEventMock = mockEventAtTime(100L);
		Answer<Object> answer = new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				clock.addEvent(nextEventMock);
				return null;
			}
		};
		doAnswer(answer).when(eventMock).run(clock);
		// when
		clock.addEvent(eventMock);
		clock.run();
		// then
		InOrder inOrder = inOrder(eventMock, nextEventMock);
		inOrder.verify(eventMock).run(clock);
		inOrder.verify(nextEventMock).run(clock);

	}

	@Test
	public void tickMethodTest() throws Exception {
		// given
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Clock clock = new Clock();
		Event mockEventAtTime = mockEventAtTime(3L);
		Event mockEventAtTime2 = mockEventAtTime(6L);
		clock.addEvent(mockEventAtTime);
		clock.addEvent(mockEventAtTime2);
		// when
		clock.tick();
		// then
		verify(mockEventAtTime).run(clock);
		verify(mockEventAtTime2, never()).run(clock);

		// when
		clock.tick();
		// then
		verify(mockEventAtTime).run(clock);
		verify(mockEventAtTime2).run(clock);

	}

	@Test
	public void canHandleAddingEventsWithTheSameTimeTest() throws Exception {
		// given
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Clock clock = new Clock();
		Event mockEventAtTime = mockEventAtTime(3L);
		Event mockEventAtTime2 = mockEventAtTime(3L);
		Answer<Object> answer = new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				clock.addEvent(mockEventAtTime2);
				return null;
			}
		};
		doAnswer(answer).when(mockEventAtTime).run(clock);
		// when
		clock.addEvent(mockEventAtTime);
		// then
		clock.run();
		verify(mockEventAtTime).run(clock);
		verify(mockEventAtTime2).run(clock);

	}

}
