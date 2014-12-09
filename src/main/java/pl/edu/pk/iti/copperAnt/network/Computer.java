package pl.edu.pk.iti.copperAnt.network;

import java.util.Random;
import java.util.UUID;

import pl.edu.pk.iti.copperAnt.gui.ComputerControl;
import pl.edu.pk.iti.copperAnt.gui.WithControl;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.DistributionTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.events.ComputerSendsEvent;

public class Computer extends Device implements WithControl {
	private Port port;
	private IPAddress ip;
	private ComputerControl control;

	public Computer() {
		this(null);
	}

	public Computer(IPAddress ip) {
		this(ip, false);
	}

	public Computer(IPAddress ip, boolean withGui) {
		this.port = new Port(this, withGui);
		this.ip = ip;
		if (withGui) {
			this.control = new ComputerControl(port.getControl());
		}
	}

	public Port getPort() {
		return port;
	}

	@Override
	public void acceptPackage(Package pack, Port inPort) {
		// assume is response for arp package
		if (pack.getType() == PackageType.DHCP && this.ip == null) {
			this.ip = new IPAddress(pack.getContent());
		} else if (pack.getType() == PackageType.ECHO_REQUEST
				&& pack.getDestinationIP() == this.ip.toString()) {
			// TODO: add event to pong
		}

		System.out.println("Computer received package");

	}

	public void initTrafic() {
		if (this.ip == null) {
			return;
		}
		Clock clock = Clock.getInstance();
		long time = clock.getCurrentTime() + this.getDelay();
		Package pack = new Package(PackageType.ECHO_REQUEST, UUID.randomUUID()
				.toString());
		pack.setSourceIP(this.ip.toString());
		IPAddress dest = this.ip;
		Random generator = new Random();
		dest.set(generator.nextInt(4) + 1, generator.nextInt(254) + 1);
		pack.setDestinationIP(dest.toString());
		ComputerSendsEvent event = new ComputerSendsEvent(time, this, pack);
		event.setIntervalGenerator(new DistributionTimeIntervalGenerator());
		clock.addEvent(event);
	}

	public void init() {
		Package pack = new Package(PackageType.DHCP, null);
		port.sendPackage(pack, getDelay());
	}

	@Override
	public ComputerControl getControl() {
		return control;
	}

}
