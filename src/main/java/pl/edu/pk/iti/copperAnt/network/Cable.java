package pl.edu.pk.iti.copperAnt.network;

public class Cable {

	Port a;
	Port b;

	CableState state;
	private long busyUntilTime;

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
		if (port == a) {
			a = null;
		} else if (port == b) {
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
		if (port != a && port != b) {
			if (a == null) {
				a = port;
				port.conntectCalble(this);
			} else if (b == null) {
				b = port;
				port.conntectCalble(this);
			}
		}
	}

	@Override
	public String toString() {
		return Integer.toHexString(hashCode()) + "[state=" + state + "]"
				+ "[busyUntilTime=" + busyUntilTime + "]";
	}

	public long getDelay() {
		// TODO uzaleznić to od długości kabla
		return 2;
	}

	public void setBusyUntil(long time) {
		this.busyUntilTime = time;
	}

	public long getBusyUntilTime() {
		return busyUntilTime;
	}
}
