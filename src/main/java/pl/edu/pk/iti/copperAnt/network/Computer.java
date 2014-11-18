package pl.edu.pk.iti.copperAnt.network;
import java.util.Random;
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
		if (pack.getType() == PackageType.DHCP && this.ip == null ) {
			this.ip = pack.getContent();
		} else if (pack.getType() == PackageType.ECHO_REQUEST && pack.getDestinationIP() == this.ip) {
			// TODO: add event to pong
		}
		
		System.out.println("Computer received package");

	}

	public void initTrafic(Clock clock) {
		if (this.ip == null) {
			return;
		}
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.ECHO_REQUEST, UUID.randomUUID().toString());
		pack.setSourceIP(this.ip);
		String[] ipParts = this.ip.split(".");
		Random generator = new Random(); 
		ipParts[ipParts.length - 1] = Integer.toString(generator.nextInt(254) + 1);
	    StringBuilder ip = new StringBuilder();

		for (int i = 0, len = ipParts.length; i < len; ++i) {
			ip.append(ipParts[i] + ".");
		}
		// ARP for MAC?
		pack.setDestinationIP(new String(ip.deleteCharAt(ip.length() - 1)));
		CoputerSendsEvent event = new CoputerSendsEvent(time, this,
				pack);
		event.setIntervalGenerator(new ConstantTimeIntervalGenerator(10));
		clock.addEvent(event);
	}
	
	public void init(Clock clock) {
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.DHCP, null);
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
