package pl.edu.pk.iti.copperAnt.gui;

import javafx.scene.layout.Pane;

public class ComputerSandbox extends AbstractControlSandbox {

	@Override
	protected void addElements(Pane root) {
		ComputerControl computer = new ComputerControl();
		computer.setLayoutX(0);
		computer.setLayoutY(200);
		root.getChildren().add(computer);

	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
