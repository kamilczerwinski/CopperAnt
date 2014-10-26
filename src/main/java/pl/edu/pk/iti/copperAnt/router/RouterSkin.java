package pl.edu.pk.iti.copperAnt.router;

import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RouterSkin extends SkinBase<Router> {

	private static Image image = new Image(RouterSkin.class.getResource(
			"/images/router.jpg").toExternalForm());

	protected RouterSkin(Router control) {
		super(control);
		initGraphics();
	}

	private void initGraphics() {
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		getChildren().setAll(imageView);

	}
}
