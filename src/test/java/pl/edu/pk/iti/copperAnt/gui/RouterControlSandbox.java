package pl.edu.pk.iti.copperAnt.gui;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.Pane;

public class RouterControlSandbox extends AbstractControlSandbox {

	@Override
	protected void addElements(Pane root) {
		for (int j = 1; j < 5; j++) {
			List<PortControl> list = new LinkedList<PortControl>();
			for (int i = 0; i < 10 * j; i++) {
				PortControl port = new PortControl();
				list.add(port);
			}
			RouterControl router = new RouterControl(list);
			router.setLayoutX(0);
			router.setLayoutY(j * 200);
			root.getChildren().add(router);
		}

	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
