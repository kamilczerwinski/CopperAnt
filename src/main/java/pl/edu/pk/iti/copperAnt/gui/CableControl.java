package pl.edu.pk.iti.copperAnt.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.shape.Line;

public class CableControl extends Control {

	public Line line;

	public CableControl() {
		line = new Line(100, 200, 300, 400);
		getChildren().add(line);
	}

	public void bindWithPort(PortControl portControl, Side side) {
		DoubleProperty xPropertyToBind;
		DoubleProperty yPropertyToBind;
		if (side == Side.START) {
			xPropertyToBind = line.startXProperty();
			yPropertyToBind = line.startYProperty();
		} else {
			xPropertyToBind = line.endXProperty();
			yPropertyToBind = line.endYProperty();

		}
		xPropertyToBind.bind(new DoubleBinding() {
			{
				super.bind(portControl.getParent().layoutXProperty());
			}

			@Override
			protected double computeValue() {
				return portControl.localToScene(0, 0).getX()
						+ portControl.getWidth() / 2;
			}
		});

		yPropertyToBind.bind(new DoubleBinding() {
			{
				super.bind(portControl.getParent().layoutYProperty());
			}

			@Override
			protected double computeValue() {
				return portControl.localToScene(0, 0).getY()
						+ portControl.getHeight() / 2;
			}
		});
	}

	public enum Side {
		START, END;
	}
}
