package pl.edu.pk.iti.copperAnt.simulation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.edu.pk.iti.copperAnt.simulation.events.ComplexMockEvent;
import pl.edu.pk.iti.copperAnt.simulation.events.Event;
import pl.edu.pk.iti.copperAnt.simulation.events.SimpleMockEvent;

public class ClockTest {

	@Test
	public void addEventTest() {
		// given
		Clock clock = new Clock();
		// when
		clock.addEvent(new SimpleMockEvent(6));
		clock.addEvent(new SimpleMockEvent(3));
		clock.addEvent(new SimpleMockEvent(1));
		clock.addEvent(new SimpleMockEvent(2));
		clock.addEvent(new SimpleMockEvent(2));
		clock.addEvent(new SimpleMockEvent(4));
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
		Event eventAt1 = mock(Event.class);
		when(eventAt1.getTime()).thenReturn(1L);
		Event eventAt2 = mock(Event.class);
		when(eventAt2.getTime()).thenReturn(2L);
		Event eventAt3 = mock(Event.class);
		when(eventAt3.getTime()).thenReturn(3L);
		Event eventAt4 = mock(Event.class);
		when(eventAt4.getTime()).thenReturn(4L);
		Event eventAt5 = mock(Event.class);
		when(eventAt5.getTime()).thenReturn(5L);
		Event eventAt6 = mock(Event.class);
		when(eventAt6.getTime()).thenReturn(6L);

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

	@Test
	public void runEventsWithConsequencesTest() throws InterruptedException {
		// given
		Clock clock = new Clock();
		Event eventMock = mock(Event.class);
		when(eventMock.getTime()).thenReturn(10L);
		Event nextEventMock = mock(Event.class);
		when(nextEventMock.getTime()).thenReturn(100L);
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
		// when
		clock.addEvent(new SimpleMockEvent(3));
		clock.addEvent(new SimpleMockEvent(6));
		// then
		clock.tick();
		assertEquals("SimpleMockEvent [time=3]\n", outContent.toString());
		clock.tick();
		assertEquals("SimpleMockEvent [time=3]\n"
				+ "SimpleMockEvent [time=6]\n", outContent.toString());

	}

	@Test
	public void canHandleAddingEventsWithTheSameTimeTest() throws Exception {
		// given
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Clock clock = new Clock();
		// when
		clock.addEvent(new ComplexMockEvent(0, 0, 10));
		// then
		clock.run();
		assertEquals("ComplexMockEvent [time=0]\n"
				+ "SimpleMockEvent [time=0]\n" + "SimpleMockEvent [time=10]\n",
				outContent.toString());

	}

}
