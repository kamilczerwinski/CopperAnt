package pl.edu.pk.iti.copperAnt.port.sandbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.pk.iti.copperAnt.port.Port;

public class SandboxMain extends Application {

	@Override
	public void start(Stage stage) throws InterruptedException {

		Pane pane = new AnchorPane();
		pane.setStyle("-fx-background-color:white;");
		Scene scene = new Scene(pane, 200, 200);

		stage.setTitle("Router sandbox");
		stage.setScene(scene);

		Port port = new Port();
		port.setSending(true);
		port.setReceiving(true);
		pane.getChildren().add(port);
		stage.setMaximized(true);
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
