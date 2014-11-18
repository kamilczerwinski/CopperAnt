package pl.edu.pk.iti.copperAnt.network;
import java.util.UUID;


import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.events.CoputerSendsEvent;

public class Computer implements Device {
	private Port port;
	private String ip;

	public Computer() {
		this.port = new Port(this);
		this.ip = null;
	}

	public Port getPort() {
		return port;
	}

	@Override
	public void acceptPackage(Package pack, Port inPort) {
		// assume is response for arp package
		if (pack.getType() == PackageType.ARP && this.ip == null ) {
			this.ip = pack.getContent();
		} else if (pack.getType() == PackageType.ECHO_REQUEST && pack.getDestinationIP() == this.ip) {
			// TODO: add event to pong
		}
		
		System.out.println("Computer received package");

	}

	public void initTrafic(Clock clock) {
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.ARP, null);
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
