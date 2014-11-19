package pl.edu.pk.iti.copperAnt.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PortControlSandbox extends AbstractControlSandbox {

	@Override
	protected void addElements(Pane root) {
		addPort1(root);
		addPort2(root);
		addPort3(root);
	}

	private void addPort1(Pane root) {
		PortControl port = new PortControl();
		root.getChildren().add(port);
	}

	private void addPort2(Pane root) {
		PortControl port = new PortControl(200, 200);
		port.setLayoutX(100);
		port.setLayoutY(100);
		root.getChildren().add(port);
	}

	private void addPort3(Pane root) {
		PortControl port = new PortControl(100, 100);
		port.setLayoutX(300);
		port.setLayoutY(300);
		port.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					port.acceptPackage();
				} else if (event.getButton() == MouseButton.SECONDARY) {
					if (port.isOn()) {
						port.turnOff();
					} else {
						port.turnOn();
					}

				}
			}
		});
		root.getChildren().add(port);
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
