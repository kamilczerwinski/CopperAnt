package pl.edu.pk.iti.copperAnt.router.sandbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.pk.iti.copperAnt.router.Router;

public class SandboxMain extends Application {

	@Override
	public void start(Stage stage) {

		Pane pane = new AnchorPane();
		pane.setStyle("-fx-background-color:cyan;");
		Scene scene = new Scene(pane, 200, 200);

		stage.setTitle("Router sandbox");
		stage.setScene(scene);

		Router router = new Router();
		router.setPrefSize(10, 10);
		router.setLayoutY(5);
		router.setLayoutX(35);
		router.setVisible(true);
		pane.getChildren().add(router);
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
