package pl.edu.pk.iti.copperAnt.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PortTest {

	@Test
	public void conntectCalbleImpactsCableFieldsTest() {
		// given
		Port port = new Port();
		Cable cable = new Cable();
		// when
		port.conntectCalble(cable);
		// then
		assertEquals(cable, port.getCable());
		assertTrue("One of cable ends is conncted to port",
				portIsConnectedToOneOfCableEnds(port, cable));
	}

	@Test
	public void disconnectCableTest() {
		Port port = new Port();
		Cable cable = new Cable();
		port.conntectCalble(cable);
		// when
		port.disconnectCable();
		// then
		assertNull(port.getCable());
		assertFalse("None of cable ends is conncted to port",
				portIsConnectedToOneOfCableEnds(port, cable));
	}

	@Test
	public void cannotConnectWhenCableExistsTest() {
		Port port = new Port();
		Cable cable1 = new Cable();
		Cable cable2 = new Cable();
		port.conntectCalble(cable1);
		// when
		port.conntectCalble(cable2);
		// then
		assertFalse(port.getCable().equals(cable2));
		assertFalse(portIsConnectedToOneOfCableEnds(port, cable2));
	}

	private boolean portIsConnectedToOneOfCableEnds(Port port, Cable cable) {
		boolean isConnectedToA = (cable.getA() == null ? false : cable.getA()
				.equals(port));
		boolean isConnectedToB = (cable.getB() == null ? false : cable.getB()
				.equals(port));
		return isConnectedToA || isConnectedToB;
	}
}
