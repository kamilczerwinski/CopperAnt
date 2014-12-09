package pl.edu.pk.iti.copperAnt.network;

import javafx.scene.control.Control;
import pl.edu.pk.iti.copperAnt.gui.CableControl;
import pl.edu.pk.iti.copperAnt.gui.WithControl;

/*																Ethernet (10Mb/s)	| FastEthernet (100Mb/s)	| GigaEthernet (1000Mb/s)

Czas trwania ramki											51.2			|						5.12					|						0.512				
64B [mikroSekundy]

Średnica domeny 												25.6  		|						2.56					|						0.256		
kolizyjnej [mikroSekundy]

Średnica domeny													5000			|						500 					|							50
kolizyjnej [metry]

http://edu.pjwstk.edu.pl/wyklady/wspsi/scb/index78.html
*/

public class Cable implements WithControl {

	//For ethernet
	int cableLength = 10;
	//Full Duplex Mode == no colision (bedzie taka opcja?)
	Boolean fullDuplex = false;

	//Bandwith unit: Megabit per second
	int bandwidth = 10;

	Port a;
	Port b;

	CableState state;
	private long busyUntilTime;
	private CableControl control;

	public Cable() {
		this(false);
	}

	public Cable(int cableLength) {
		this.cableLength = cableLength;
	}

	public Cable(int cableLength, int bandwidth) {
		this.cableLength = cableLength;
		this.bandwidth = bandwidth;
	}

	public Cable(boolean withGui) {
		state = CableState.IDLE;
		if (withGui) {
			this.control = new CableControl();
		}
	}

	public CableState getState() {
		return state;
	}

	public void setState(CableState state) {
		this.state = state;
		if (this.control != null) {
			control.setState(state);
		}
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
				if (getControl() != null && port.getControl() != null) {
					control.bindWithPort(port.getControl(),
							CableControl.Side.START);
				}
			} else if (b == null) {
				b = port;
				port.conntectCalble(this);
				if (getControl() != null && port.getControl() != null) {
					control.bindWithPort(port.getControl(),
							CableControl.Side.END);
				}
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

	@Override
	public Control getControl() {
		return control;
	}

	public void setControl(CableControl control) {
		this.control = control;
	}

	public int getBandwidth() {
		return bandwidth;
	}
}
