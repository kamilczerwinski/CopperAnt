package pl.edu.pk.iti.copperAnt.network;

public class Cable {
	Port A;
	Port B;
	CableState state;

	public Cable() {
		state = CableState.IDLE;
	}

	public CableState getState() {
		return state;
	}

	public void setState(CableState state) {
		this.state = state;
	}

	public Port getOtherEnd(Port port) {
		if (port.equals(A)) {
			return B;
		} else {
			return A;
		}

	}

}
