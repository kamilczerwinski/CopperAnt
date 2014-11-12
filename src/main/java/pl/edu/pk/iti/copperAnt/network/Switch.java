package pl.edu.pk.iti.copperAnt.network;

public class Switch implements Device {

	@Override
	public void acceptPackage(Package pack) {
		// TODO Auto-generated method stub

	}
	public String getIp() {
		return "";
	}
	public String getMac() {
		return mac;
	}

	private String mac;

	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}
}
