package pl.edu.pk.iti.copperAnt.network;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.events.Event;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;


public class RouterTest {
	@Test
	public void testEmtpyRoutingTable() {
		Clock clock = mock(Clock.class);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		doNothing().when(clock).addEvent(eventCaptor.capture());
		when(clock.getCurrentTime()).thenReturn(11L);
		Router router = new Router(4, clock);
		Package pack = new Package();
		pack.setDestinationIP("AAa");
		PortSendsEvent expected = new PortSendsEvent(clock.getCurrentTime() + router.getDelay(), router.getPort(0), pack);
		router.acceptPackage(pack, router.getPort(0));
		List<Event> capturedEvent = eventCaptor.getAllValues();
		assertEquals(capturedEvent.size(), 1);
		assertEquals(capturedEvent.get(0).toString(), expected.toString());
			
		
	}
	
	@Test
	public void testNotEmtpyRoutingTable() {
		Clock clock = mock(Clock.class);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		doNothing().when(clock).addEvent(eventCaptor.capture());
		when(clock.getCurrentTime()).thenReturn(11L);
		Router router = new Router(4, clock);
		router.addRouting("testowy", router.getPort(2));
		Package pack = new Package();
		pack.setDestinationIP("testowy");
		PortSendsEvent expected = new PortSendsEvent(clock.getCurrentTime() + router.getDelay(), router.getPort(2), pack);
		router.acceptPackage(pack, router.getPort(0));
		List<Event> capturedEvent = eventCaptor.getAllValues();
		assertEquals(capturedEvent.size(), 1);
		assertEquals(capturedEvent.get(0).toString(), expected.toString());
			
		
	}
	
	@Test
	public void testRequestForIp() {
		Clock clock = mock(Clock.class);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		doNothing().when(clock).addEvent(eventCaptor.capture());
		when(clock.getCurrentTime()).thenReturn(11L);
		
		Router router = new Router(4, clock);
		Package pack = new Package(PackageType.DHCP, null);
		pack.setSourceMAC("aaaaaa");
		router.acceptPackage(pack, router.getPort(0));
		List<Event> capturedEvent = eventCaptor.getAllValues();
		assertEquals(capturedEvent.size(), 1);
		Event event =  ((Event)capturedEvent.get(0));
		assertTrue(event.getPackage().getContent() != null);
		assertEquals(PackageType.DHCP, event.getPackage().getType());
	}

	@Test
	public void testRequestForIp2() {
		Clock clock = mock(Clock.class);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		doNothing().when(clock).addEvent(eventCaptor.capture());
		when(clock.getCurrentTime()).thenReturn(11L);
		Properties config = new Properties();
		config.setProperty("numbersOfPorts", "4");
		config.setProperty("DHCPstartIP", "192.168.0.1");
					
		Router router = new Router(config, clock);
		Package pack = new Package(PackageType.DHCP, null);
		pack.setSourceMAC("aaaaaa");
		router.acceptPackage(pack, router.getPort(0));
		List<Event> capturedEvent = eventCaptor.getAllValues();
		assertEquals(capturedEvent.size(), 1);
		Event event =  ((Event)capturedEvent.get(0));
		assertEquals("192.168.0.2", event.getPackage().getContent());
		assertEquals(PackageType.DHCP, event.getPackage().getType());
	}	
		
		
	@Test
	public void testTTl0() {
		Clock clock = mock(Clock.class);
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		doNothing().when(clock).addEvent(eventCaptor.capture());
		when(clock.getCurrentTime()).thenReturn(11L);
		Properties config = new Properties();
		config.setProperty("numbersOfPorts", "4");
		config.setProperty("DHCPstartIP", "192.168.0.1");
		
					
		Router router = new Router(config, clock);
		Package pack = new Package(PackageType.ECHO_REQUEST, "wiadomosc");
		pack.setSourceMAC("aaaaaa");
		pack.setDestinationIP("192.168.222.222");
		for (int i = 0; i < 101; ++i)
			pack.validTTL();
		router.acceptPackage(pack, router.getPort(0));
		List<Event> capturedEvent = eventCaptor.getAllValues();
		assertEquals(capturedEvent.size(), 1);
		Event event =  ((Event)capturedEvent.get(0));
		assertEquals("TTL<0", event.getPackage().getContent());
		assertEquals(PackageType.DESTINATION_UNREACHABLE, event.getPackage().getType());
	}	
	
	
}
