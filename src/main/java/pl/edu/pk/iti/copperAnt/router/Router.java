package pl.edu.pk.iti.copperAnt.router;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import jfxtras.labs.util.event.MouseControlUtil;
import jfxtras.scene.control.window.Window;
import jfxtras.scene.layout.HBox;

public class Router extends Control {

	private Window window;
	private boolean windowIsVisible;
	private SimpleBooleanProperty isWorkingProperty;

	public Window getWindow() {
		return window;
	}

	public Router() {
		isWorkingProperty = new SimpleBooleanProperty(true);
		setListeners();
		window = new Window("Router");
		window.setMinSize(200, 200);
		windowIsVisible = false;
		HBox statusHBox = new HBox();
		Label isOnLabel = new Label();
		isOnLabel.textProperty().bind(this.isWorkingProperty.asString());

		statusHBox.getChildren().add(isOnLabel);
		window.getContentPane().getChildren().add(statusHBox);
		window.setVisible(windowIsVisible);

	}

	private void setListeners() {
		MouseControlUtil.makeDraggable(this);
		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					windowIsVisible = !windowIsVisible;
					window.setVisible(windowIsVisible);
				} else {
					isWorkingProperty.set(!isWorkingProperty.get());
				}

			}
		});

	}

	@Override
	protected Skin<Router> createDefaultSkin() {
		return new RouterSkin(this);
	}
}
