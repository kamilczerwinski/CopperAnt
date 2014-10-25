package pl.edu.pk.iti.copperAnt.router;

import javafx.scene.control.SkinBase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RouterSkin extends SkinBase<Router> {
	private Circle frame;

	protected RouterSkin(Router control) {
		super(control);
		initGraphics();
	}

	private void initGraphics() {
		frame = new Circle(50, 50, 50);
		frame.setFill(Color.RED);
		frame.setVisible(true);
		getChildren().setAll(frame);
	}

}
