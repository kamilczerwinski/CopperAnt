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
		return m_Mac;
	}
	
	private String m_Mac;
}
