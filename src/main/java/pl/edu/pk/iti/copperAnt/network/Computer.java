package pl.edu.pk.iti.copperAnt.network;
import java.util.UUID;


import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.events.CoputerSendsEvent;

public class Computer implements Device {
	private Port port;
	private String mac;
	private String ip;

	public Computer() {
		this.port = new Port(this);
		this.ip = UUID.randomUUID().toString();
		this.mac = UUID.randomUUID().toString();
	}

	public Port getPort() {
		return port;
	}

	@Override
	public void acceptPackage(Package pack, Port inPort) {
		System.out.println("Computer received package");

	}

	public void initTrafic(Clock clock) {
		long time = clock.getCurrentTime();
		Package pack = new Package();
		pack.setDestinationIP(UUID.randomUUID().toString());
		pack.setDestinationMAC(UUID.randomUUID().toString());
		pack.setSourceIP(this.ip);
		pack.setSourceMAC(this.mac);
		CoputerSendsEvent event = new CoputerSendsEvent(time, this,
				pack);
		event.setIntervalGenerator(new ConstantTimeIntervalGenerator(10));
		clock.addEvent(event);
	}
	@Override
	public int getDelay() {
		return 0;
	}

}
