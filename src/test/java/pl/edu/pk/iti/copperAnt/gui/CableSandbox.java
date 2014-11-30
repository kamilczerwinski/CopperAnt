package pl.edu.pk.iti.copperAnt.gui;

import javafx.scene.layout.Pane;

public class CableSandbox extends AbstractControlSandbox {

	@Override
	protected void addElements(Pane root) {
		CableControl cableControl = new CableControl();
		root.getChildren().add(cableControl);
		PortControl portControl = new PortControl();
		ComputerControl computer = new ComputerControl(portControl);
		PortControl portControl2 = new PortControl();
		ComputerControl computer2 = new ComputerControl(portControl2);
		cableControl.bindWithPort(portControl, CableControl.Side.START);
		cableControl.bindWithPort(portControl2, CableControl.Side.END);
		root.getChildren().add(computer);
		root.getChildren().add(computer2);

	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
