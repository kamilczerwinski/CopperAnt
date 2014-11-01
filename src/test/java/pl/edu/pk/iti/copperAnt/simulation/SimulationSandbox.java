package pl.edu.pk.iti.copperAnt.simulation;

import org.junit.Test;

import pl.edu.pk.iti.copperAnt.network.Cable;
import pl.edu.pk.iti.copperAnt.network.Port;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class SimulationSandbox {

	@Test
	public void test1() {
		Clock clock = new Clock();
		clock.setFinishCondition(new MaxTimeFinishCondition(100));
		Port port1 = new Port();
		Port port2 = new Port();
		Cable cable = new Cable();
		cable.insertInto(port1);
		cable.insertInto(port2);

		clock.addEvent(new PortSendsEvent(0, port1)
				.withIntervalGenerator(new ConstantTimeIntervalGenerator(3)));
		clock.addEvent(new PortSendsEvent(1, port2)
				.withIntervalGenerator(new ConstantTimeIntervalGenerator(5)));
		clock.run();
	}

}
