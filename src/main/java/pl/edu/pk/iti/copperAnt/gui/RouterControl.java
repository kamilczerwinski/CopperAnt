package pl.edu.pk.iti.copperAnt.gui;

import java.util.List;

import javafx.scene.image.Image;

public class RouterControl extends MultiportDeviceControl {

	public RouterControl(List<PortControl> portList) {
		super(portList);
	}

	@Override
	protected Image getIconImage(double size) {
		return new Image(PortControl.class.getResource("/images/router.png")
				.toExternalForm(), size, size, true, false);
	}

}
