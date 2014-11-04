package pl.edu.pk.iti.copperAnt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.edu.pk.iti.copperAnt.network.TestHelper.portIsConnectedToOneOfCableEnds;

import java.util.List;
import java.util.Properties;

import org.junit.Test;

import pl.edu.pk.iti.copperAnt.Configuration;
import pl.edu.pk.iti.copperAnt.network.Cable;
import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.MockDevice;

public class ConfigurationTest {

	@Test
	public void testRead() {
		// given
		Configuration conf = Configuration.getInstance();
		// FIXME: jak sie mokuje w Javie?
		conf.init("./configuration.xml");
		List<Properties> data = conf.getData();
		
		assertEquals(1, data.size());
		assertEquals("router", data.get(0).getProperty("type"));
		assertEquals("4", data.get(0).getProperty("portCount"));

	}
	
}
