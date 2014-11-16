package pl.edu.pk.iti.copperAnt.gui;

import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PortControl extends Control {
	private static final int defaultIconHeight = 25;
	private static final int defaultIconWidth = 25;
	private int width;
	private int height;
	private Rectangle redRectangle;
	private Rectangle greenRectangle;

	public PortControl() {
		this(defaultIconWidth, defaultIconHeight);
	}

	public PortControl(int width, int height) {
		this.width = width;
		this.height = height;
		Image image = new Image(PortControl.class.getResource(
				"/images/portMini.png").toExternalForm(), width, height, false,
				false);

		ImageView imageView = new ImageView();
		imageView.setImage(image);
		getChildren().add(imageView);
		setWidth(width);
		setHeight(height);

		prepareDiods();
	}

	private void prepareDiods() {
		redRectangle = new Rectangle(width / 2, height / 10);
		redRectangle.setX(width / 2);
		turnOnRedDiode(false);
		greenRectangle = new Rectangle(width / 2, height / 10);
		turnOnGreenDiode(false);
		getChildren().add(redRectangle);
		getChildren().add(greenRectangle);
	}

	public void turnOnRedDiode(boolean on) {
		if (on) {
			redRectangle.setFill(Color.RED);
		} else {
			redRectangle.setFill(Color.GRAY);
		}
	}

	public void turnOnGreenDiode(boolean on) {
		if (on) {
			greenRectangle.setFill(Color.GREENYELLOW);
		} else {
			greenRectangle.setFill(Color.GRAY);
		}
	}
}
