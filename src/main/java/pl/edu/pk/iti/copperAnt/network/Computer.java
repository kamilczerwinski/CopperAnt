package pl.edu.pk.iti.copperAnt.network;


import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.xml.ws.Response;

import pl.edu.pk.iti.copperAnt.gui.ComputerControl;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.events.ComputerSendsEvent;
import pl.edu.pk.iti.copperAnt.simulation.events.PortSendsEvent;

public class Computer implements Device {
	private Port port;
	private IPAddress ip;
	private ComputerControl control;
	private HashMap<String, String> arpTable = new HashMap<String, String>();
	private Clock clock;
	private static int TIMEOUT_ADDRESS_RESOLVE = 10;

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
		} else if (pack.getType() == PackageType.ARP )
			if (pack.getContent() == null && pack.getHeader() == this.ip.toString()) {
				Package outPack = new Package(PackageType.ARP, this.port.getMAC());
				outPack.setDestinationIP(pack.getDestinationIP());
				outPack.setDestinationMAC(pack.getDestinationMAC());
				long time = clock.getCurrentTime();
				clock.addEvent(new PortSendsEvent(time, this.port, outPack));
			
			} else {
				arpTable.put(pack.getSourceIP(), pack.getContent());
			}
		if (pack.getDestinationIP() == this.ip.toString()) {
			if (pack.getType() == PackageType.ECHO_REQUEST) {
			// TODO: add event to pong
			} 
		}
		
		

		System.out.println("Computer received package");

	}

	public void initTrafic(Clock clock) {
		if (this.ip == null) {
			return;
		}
		this.clock = clock;
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.ECHO_REQUEST, UUID.randomUUID()
				.toString());
		pack.setSourceIP(this.ip.toString());
		IPAddress dest = this.ip;
		Random generator = new Random();
		dest.set(generator.nextInt(4) + 1, generator.nextInt(254) + 1);
		pack.setDestinationIP(dest.toString());
		String destMAC = null;
		if (arpTable.containsKey(dest.toString())) {
			destMAC = arpTable.get(destMAC);
		} else {
			if (resolveAddress(dest.toString())) {
				destMAC = arpTable.get(destMAC);

			} else {
				return;
			}
			
		}
		
		
		ComputerSendsEvent event = new ComputerSendsEvent(time, this, pack);
		event.setIntervalGenerator(new ConstantTimeIntervalGenerator(10));
		clock.addEvent(event);
	}
	private boolean resolveAddress(String toResolve) {
		int i = 0;
		while (!arpTable.containsKey(toResolve) || i < TIMEOUT_ADDRESS_RESOLVE) {
			long time = clock.getCurrentTime();
			Package pack = new Package(PackageType.ARP, toResolve, null);
			pack.setSourceIP(this.ip.toString());
			pack.setDestinationIP(this.ip.getBrodcast());
			pack.setDestinationMAC("00:00:00:00:00");
			clock.addEvent(new PortSendsEvent(time, this.port, pack));
			i++;
		}
		if (arpTable.containsKey(toResolve)) {
			return true;
		}
		return false;
		
	}
	public void init(Clock clock) {
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.DHCP, null);
		pack.setSourceIP(this.ip.toString());
		clock.addEvent(new PortSendsEvent(time, this.port, pack));
	}

	@Override
	public int getDelay() {
		return 0;
	}

	public ComputerControl getControl() {
		return control;
	}

}
