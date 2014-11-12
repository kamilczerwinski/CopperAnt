package pl.edu.pk.iti.copperAnt.simulation;

import pl.edu.pk.iti.copperAnt.network.Device;
import pl.edu.pk.iti.copperAnt.network.Package;

public class MockDevice implements Device {

	@Override
	public void acceptPackage(Package pack) {

	}

	@Override
	public int getDelay() {
		return 0;
	}

	@Override
	public String getIp() {
		return null;
	}

	@Override
	public String getMac() {
		return null;
	}

}
