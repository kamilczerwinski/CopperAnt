package pl.edu.pk.iti.copperAnt.network;

public class Port {
	Cable cable;

	public Cable getCable() {
		return cable;
	}

	Device device;

	public void conntectCalble(Cable cable) throws Exception {
		if (this.cable == null) {
			this.cable = cable;
		} else {
			throw new Exception("Cannot connect cable. Port is already used");
		}
	}

	public void disconnectCable() {
		this.cable = null;
	}
}
