package pl.edu.pk.iti.copperAnt.network;

public class Computer implements Device {
	private Port port;

	public Computer() {
		this.port = new Port();
	}

	public Port getPort() {
		return port;
	}

	@Override
	public void acceptPackage(Package pack) {
		// TODO Auto-generated method stub

	}

}
