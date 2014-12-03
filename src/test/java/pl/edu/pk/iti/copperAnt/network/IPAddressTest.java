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

public class IPAddressTest {

	@Test
	public void testCreate() {
		IPAddress ip = new IPAddress("192.168.0.1");
		assertEquals(ip.toString(), "192.168.0.1");
	}
	@Test
	public void testCreate2() {
		IPAddress ip = new IPAddress("192.168.0.1", "255.255.255.128");
		assertEquals(ip.toString(), "192.168.0.1");
	}
	
	@Test
	public void testincrement() {
		IPAddress ip = new IPAddress("192.168.0.1", "255.255.255.128");
		ip.increment();
		assertEquals(ip.toString(), "192.168.0.2");
	}
	
	@Test
	public void testincrement2() {
		IPAddress ip = new IPAddress("192.168.0.1", "255.255.255.128");
		ip.increment(3);
		assertEquals(ip.toString(), "192.168.1.1");
	}
	@Test
	public void testNetwork() {
		IPAddress ip = new IPAddress("192.168.0.1", "255.255.255.0");
		assertEquals(ip.getNetwork(), "192.168.0.0");
	}
	@Test
	public void testSet() {
		IPAddress ip = new IPAddress("192.168.0.1", "255.255.255.128");
		ip.set(3, 127);
		assertEquals(ip.toString(), "192.168.127.1");
	}
	@Test
	public void testIsInSubnet() {
		assertTrue(IPAddress.isInSubnet("192.168.0.12", "192.168.0.0", "255.255.255.0"));
	}
	
	
	@Test
	public void testIsNotInSubnet() {
		assertFalse(IPAddress.isInSubnet("192.168.12.1", "192.168.0.0", "255.255.255.128"));
	}
}
