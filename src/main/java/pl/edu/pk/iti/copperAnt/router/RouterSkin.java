package pl.edu.pk.iti.copperAnt.router;

import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.edu.pk.iti.copperAnt.port.Port;

public class RouterSkin extends SkinBase<Router> {

	private static Image image = new Image(RouterSkin.class.getResource(
			"/images/router.png").toExternalForm());

	protected RouterSkin(Router control) {
		super(control);
		initGraphics();
	}

	private void initGraphics() {
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		getChildren().add(imageView);
		int numberOfPorts = 3;
		for (int i = 0; i < numberOfPorts; i++) {
			Port port = new Port();
			port.setLayoutX(100 * i);
			port.setTranslateX(-30 * i);
			getChildren().add(port);

		}

	}
}
