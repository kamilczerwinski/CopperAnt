package pl.edu.pk.iti.copperAnt.network;

import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.events.CoputerSendsEvent;

public class Computer implements Device {
	private Port port;
	private String m_Mac;
	private String m_ip;

	public Computer() {
		this.port = new Port(this);
	}

	public Port getPort() {
		return port;
	}

	@Override
	public void acceptPackage(Package pack) {
		System.out.println("Computer received package");

	}

	public void initTrafic(Clock clock) {
		long time = clock.getCurrentTime();
		CoputerSendsEvent event = new CoputerSendsEvent(time, this,
				new Package());
		event.setIntervalGenerator(new ConstantTimeIntervalGenerator(10));
		clock.addEvent(event);
	}

}
