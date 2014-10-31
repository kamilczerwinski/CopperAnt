package pl.edu.pk.iti.copperAnt.network;


public class Cable {

	Port a;

	Port b;

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
		if (port.equals(a)) {
			return b;
		} else {
			return a;
		}

	}

	public void ejectFromPort(Port port) {
		if (port.equals(a)) {
			a = null;
		} else if (port.equals(b)) {
			b = null;
		}
		port.disconnectCable();

	}

	public Port getA() {
		return a;
	}

	public void setA(Port a) {
		this.a = a;
	}

	public Port getB() {
		return b;
	}

	public void setB(Port b) {
		this.b = b;
	}

	public void insertInto(Port port) {
		if (a == null) {
			a = port;
			port.conntectCalble(this);
		} else if (b == null) {
			b = port;
			port.conntectCalble(this);
		}

	}
}
