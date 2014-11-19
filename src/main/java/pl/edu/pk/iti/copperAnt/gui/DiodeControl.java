package pl.edu.pk.iti.copperAnt.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DiodeControl extends Control {
	private Color color;
	private Rectangle rectangle;
	private long blinkUntil;
	private AnimationTimer timer;

	public DiodeControl(int width, int height, Color color) {
		this.color = color;
		setWidth(width);
		setHeight(height);

		rectangle = new Rectangle(width, height);
		getChildren().add(rectangle);
		turnOff();

		this.timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				turnOn();
				if (now > blinkUntil) {
					turnOff();
					this.stop();
				}
			}
		};

	}

	public void turnOn() {
		rectangle.setFill(this.color);
	}

	public void turnOff() {
		rectangle.setFill(Color.GRAY);
	}

	public void blink() {
		blinkUntil = System.nanoTime() + 100000000;
		timer.start();
	}
}
