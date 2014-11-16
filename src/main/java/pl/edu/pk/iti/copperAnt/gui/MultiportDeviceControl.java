package pl.edu.pk.iti.copperAnt.gui;

import java.util.List;

import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import jfxtras.labs.util.event.MouseControlUtil;

public abstract class MultiportDeviceControl extends Control {
	private static final double placeForIconHeight = 100;

	public MultiportDeviceControl(List<PortControl> portList) {
		MouseControlUtil.makeDraggable(this);
		double portWith = portList.get(0).getWidth();
		double portHeight = portList.get(0).getHeight();
		setWidth(Math.max(portList.size() * portWith, placeForIconHeight));
		setHeight(placeForIconHeight + portHeight);
		drawIcon(placeForIconHeight);
		drawPortsWithLines(portList, placeForIconHeight);
		drawBorder();
	}

	private void drawBorder() {
		Rectangle rectangle = new Rectangle(getWidth(), getHeight());
		rectangle.setFill(null);
		rectangle.setStrokeWidth(1);
		rectangle.setStroke(Color.BLACK);
		getChildren().add(rectangle);
	}

	private void drawIcon(double placeForIconHeight) {
		Image image = getIconImage(placeForIconHeight - 0.1
				* placeForIconHeight);

		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setLayoutX(getWidth() / 2 - image.getWidth() / 2);
		imageView.setLayoutY(placeForIconHeight / 2 - image.getHeight() / 2);
		getChildren().add(imageView);
	}

	private void drawPortsWithLines(List<PortControl> portList,
			double placeForIconHeight) {
		for (int i = 0; i < portList.size(); i++) {
			double portX = i * portList.get(0).getWidth();
			PortControl port = portList.get(i);
			port.setLayoutX(portX);
			port.setLayoutY(placeForIconHeight);
			Line line = new Line(portX + port.getWidth() / 2,
					placeForIconHeight, getWidth() / 2, placeForIconHeight / 2);
			getChildren().add(line);
			getChildren().add(port);
		}
	}

	protected abstract Image getIconImage(double size);
}
