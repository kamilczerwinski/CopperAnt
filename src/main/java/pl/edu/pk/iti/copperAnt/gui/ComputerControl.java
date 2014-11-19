package pl.edu.pk.iti.copperAnt.gui;

import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jfxtras.labs.util.event.MouseControlUtil;

public class ComputerControl extends Control {
	private final static int defaultSize = 100;

	public ComputerControl() {
		MouseControlUtil.makeDraggable(this);
		setWidth(defaultSize);
		setHeight(defaultSize);
		drawIcon();
		drawPort();
	}

	private void drawPort() {
		PortControl port = new PortControl((int) getWidth() / 3,
				(int) getHeight() / 3);
		port.setLayoutX(getWidth() - getWidth() / 3);
		port.setLayoutY(getHeight() - getHeight() / 3);
		getChildren().add(port);

	}

	private void drawIcon() {
		Image image = getIconImage(getHeight());

		ImageView imageView = new ImageView();
		imageView.setImage(image);
		getChildren().add(imageView);
	}

	protected Image getIconImage(double size) {
		return new Image(PortControl.class.getResource("/images/pc.png")
				.toExternalForm(), size, size, true, true);
	}
}
