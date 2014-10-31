package pl.edu.pk.iti.copperAnt.network;


public class Port {
	Cable cable;

	public Cable getCable() {
		return cable;
	}

	Device device;

	public void conntectCalble(Cable cable) {
		if (this.cable == null) {
			this.cable = cable;
			cable.insertInto(this);
		}
	}

	public void disconnectCable() {
		if (this.cable != null) {
			this.cable = null;
			cable.ejectFromPort(this);
		}
	}
}
