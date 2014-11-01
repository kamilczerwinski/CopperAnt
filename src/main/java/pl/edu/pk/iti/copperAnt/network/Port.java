package pl.edu.pk.iti.copperAnt.network;

public class Port {
	Cable cable;
	final Device device;

	public Device getDevice() {
		return device;
	}

	public Port(Device device) {
		super();
		this.device = device;
	}

	public Cable getCable() {
		return cable;
	}

	public void conntectCalble(Cable cable) {
		if (this.cable == null) {
			this.cable = cable;
			cable.insertInto(this);
		}
	}

	public void disconnectCable() {
		if (this.cable != null) {
			Cable cableToDisconnect = this.cable;
			this.cable = null;
			cableToDisconnect.ejectFromPort(this);
		}
	}

	@Override
	public String toString() {
		return "[" + Integer.toHexString(hashCode()) + "]";
	}

}
