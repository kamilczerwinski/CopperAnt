package pl.edu.pk.iti.copperAnt.network;

public interface Device extends WithDelay {

	public void acceptPackage(Package pack);
	public String getIp();
	public String getMac();
}
