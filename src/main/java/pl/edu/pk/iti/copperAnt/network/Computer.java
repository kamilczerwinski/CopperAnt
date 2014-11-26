package pl.edu.pk.iti.copperAnt.network;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import pl.edu.pk.iti.copperAnt.gui.ComputerControl;
import pl.edu.pk.iti.copperAnt.simulation.Clock;
import pl.edu.pk.iti.copperAnt.simulation.ConstantTimeIntervalGenerator;
import pl.edu.pk.iti.copperAnt.simulation.events.ARPResolveEvent;
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

	public void addKnownHost(String ip) {
		this.arpTable.put(ip, null);
	}

	public void addKnownHost(String ip, String mac) {
		this.arpTable.put(ip, mac);
	}

	public boolean knownHost(String ip) {
		return arpTable.containsKey(ip);
	}

	public String getKnownHost(String ip) {
		return arpTable.get(ip);
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
		} else if (pack.getType() == PackageType.ARP_REQ)
			if (pack.getContent() == null
					&& pack.getHeader() == this.ip.toString()) {
				Package outPack = new Package(PackageType.ARP_REP,
						this.port.getMAC());
				outPack.setDestinationIP(pack.getSourceIP());
				outPack.setDestinationMAC(pack.getSourceMAC());
				long time = clock.getCurrentTime();
				clock.addEvent(new PortSendsEvent(time, this.port, outPack));

			} else {
				arpTable.put(pack.getSourceIP(), pack.getContent());
			}
		if (pack.getDestinationIP() == this.ip.toString()) {
			if (pack.getType() == PackageType.ECHO_REQUEST) {
				// TODO: add event to pong
			} else if (pack.getType() == PackageType.ARP_REP) {
				arpTable.put(pack.getSourceIP(), pack.getContent());

			}
		}

		System.out.println("Computer received package");

	}

	public void initTrafic(Clock clock) {
		if (this.ip == null) {
			return;
		}
		ComputerSendsEvent event = null;
		this.clock = clock;
		long time = clock.getCurrentTime();

		Package pack = new Package(PackageType.ECHO_REQUEST, UUID.randomUUID()
				.toString());
		pack.setSourceIP(this.ip.toString());
		IPAddress dest = this.ip;
		Random generator = new Random();
		if (generator.nextBoolean()) {
			Set<String> ipAddreses = arpTable.keySet();
			for (String ip : arpTable.keySet()) {
				if (generator.nextInt(100) > 50) {
					dest = new IPAddress(ip);
				}
			}
		} else {
			dest.set(generator.nextInt(4) + 1, generator.nextInt(254) + 1);
		}
		pack.setDestinationIP(dest.toString());
		String destMAC = null;
		if (arpTable.containsKey(dest.toString())) {
			destMAC = arpTable.get(destMAC);
			event = new ComputerSendsEvent(time, this, pack);
			event.setIntervalGenerator(new ConstantTimeIntervalGenerator(10));
		} else {
			Package resolvePack = new Package(PackageType.ARP_REQ,
					dest.toString());
			resolvePack.setDestinationMAC("00:00:00:00:00");
			ComputerSendsEvent eventAfter = new ComputerSendsEvent(time, this,
					pack);
			eventAfter.setIntervalGenerator(new ConstantTimeIntervalGenerator(
					10));
			event = new ARPResolveEvent(time + getDelay(), this, resolvePack,
					eventAfter);

		}

		clock.addEvent(event);
	}

	public void init(Clock clock) {
		long time = clock.getCurrentTime();
		Package pack = new Package(PackageType.DHCP, null);
		clock.addEvent(new PortSendsEvent(time, this.port, pack));
	}

	@Override
	public int getDelay() {
		Random generator = new Random();
		return generator.nextInt(200);
	}

	public ComputerControl getControl() {
		return control;
	}

	public String getIP() {
		return this.ip.toString();
	}
}
