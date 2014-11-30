package pl.edu.pk.iti.copperAnt.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class SwitchControl extends MultiportDeviceControl {

	public SwitchControl(List<PortControl> portList) {
		super(portList);
	}

	@Override
	protected Image getIconImage(double size) {
		return new Image(PortControl.class.getResource("/images/switch.png")
				.toExternalForm(), size, size, true, false);
	}

	public static SwitchControl prepareSwithcWithPorts(int numberOfPorts) {
		List<PortControl> list = new ArrayList<PortControl>(numberOfPorts);
		for (int i = 0; i < numberOfPorts; i++) {
			list.add(new PortControl());
		}
		return new SwitchControl(list);
	}

}
