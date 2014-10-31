package pl.edu.pk.iti.copperAnt.port;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.edu.pk.iti.copperAnt.router.RouterSkin;

public class Port extends Control {
	BooleanProperty sending;

	public boolean getSending() {
		return sending.get();
	}

	public void setSending(boolean sending) {
		this.sending.set(sending);
	}

	public boolean getReceiving() {
		return receiving.get();
	}

	public void setReceiving(boolean receiving) {
		this.receiving.set(receiving);
	}

	BooleanProperty receiving;

	public Port() {
		this.receiving = new SimpleBooleanProperty(false);
		this.sending = new SimpleBooleanProperty(false);
		Image image = new Image(RouterSkin.class.getResource(
				"/images/portMini.png").toExternalForm());
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setScaleX(0.5);
		imageView.setScaleY(0.5);
		Circle circle = new Circle(3);
		circle.setLayoutX(15);
		circle.setLayoutY(15);
		circle.setFill(Color.RED);
		Circle circle2 = new Circle(3);
		circle2.setLayoutX(25);
		circle2.setLayoutY(15);
		circle2.setFill(Color.GREEN);
		circle.visibleProperty().bindBidirectional(this.sending);
		circle2.visibleProperty().bindBidirectional(this.receiving);
		getChildren().add(imageView);
		getChildren().add(circle);
		getChildren().add(circle2);

	}
}
