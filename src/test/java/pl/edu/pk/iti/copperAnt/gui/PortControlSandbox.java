package pl.edu.pk.iti.copperAnt.gui;

import javafx.scene.layout.Pane;

public class PortControlSandbox extends AbstractControlSandbox {

	@Override
	protected void addElements(Pane root) {
		final PortControl port = new PortControl();
		root.getChildren().add(port);
		final PortControl port2 = new PortControl(200, 200);
		port2.setLayoutX(100);
		port2.setLayoutY(100);
		root.getChildren().add(port2);
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
