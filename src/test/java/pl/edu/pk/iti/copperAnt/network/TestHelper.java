package pl.edu.pk.iti.copperAnt.network;

public class TestHelper {
	public static boolean portIsConnectedToOneOfCableEnds(Port port, Cable cable) {
		boolean isConnectedToA = (cable.getA() == null ? false : cable.getA()
				.equals(port));
		boolean isConnectedToB = (cable.getB() == null ? false : cable.getB()
				.equals(port));
		return isConnectedToA || isConnectedToB;
	}
}
