package pl.edu.pk.iti.copperAnt.router;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class Router extends Control {

	@Override
	protected Skin<Router> createDefaultSkin() {
		return new RouterSkin(this);
	}
}
