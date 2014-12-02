package pl.edu.pk.iti.copperAnt;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pk.iti.copperAnt.gui.SimulationCanvas;


public class MainApp extends Application {

	private static final Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	public void start(Stage stage) throws Exception {
		log.info("Starting Hello JavaFX and Maven demonstration application");

		String fxmlFile = "/fxml/main.fxml";
		log.debug("Loading FXML for main view from: {}", fxmlFile);
		FXMLLoader loader = new FXMLLoader();
		BorderPane rootNode = (BorderPane) loader.load(getClass().getResourceAsStream(
				fxmlFile));

		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("Plik");
		Menu menuSimulation = new Menu("Symulacja");
		Menu menuHelp = new Menu("Pomoc");

		menuBar.getMenus().add(menuFile);
			MenuItem fileNew = new MenuItem("Nowy");
				fileNew.setOnAction(e -> defaultAction(stage));
			menuFile.getItems().add(fileNew);
	
			MenuItem fileLoad = new MenuItem("Wczytaj");
				fileLoad.setOnAction(e -> defaultAction(stage));
			menuFile.getItems().add(fileLoad);
	
			MenuItem fileSave = new MenuItem("Zapisz");
				fileSave.setOnAction(e -> defaultAction(stage));
			menuFile.getItems().add(fileSave);
			
			MenuItem fileClose = new MenuItem("Zakończ");
			fileClose.setOnAction(e -> defaultAction(stage));
			menuFile.getItems().add(fileClose);

		menuBar.getMenus().add(menuSimulation);
			MenuItem simulationRun = new MenuItem("Start");
				simulationRun.setOnAction(e -> defaultAction(stage));
			menuSimulation.getItems().add(simulationRun);
		
			MenuItem simulationPause = new MenuItem("Pauza");
				simulationPause.setOnAction(e -> defaultAction(stage));
			menuSimulation.getItems().add(simulationPause);
		
			MenuItem simulationStop = new MenuItem("Stop");
				simulationStop.setOnAction(e -> defaultAction(stage));
			menuSimulation.getItems().add(simulationStop);
			
		menuBar.getMenus().add(menuHelp);		
			MenuItem helpAbout= new MenuItem("O programie");
				helpAbout.setOnAction(e -> helpAbout(stage));
			menuHelp.getItems().add(helpAbout);
		
			MenuItem helpAuthors = new MenuItem("O autorach");
				helpAuthors.setOnAction(e -> helpAuthors(stage));
			menuHelp.getItems().add(helpAuthors);

		rootNode.setTop(menuBar);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(400, 400);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setContent(new SimulationCanvas());
		
		rootNode.setCenter(scrollPane);
		
		
		

		log.debug("Showing JFX scene");
		Scene scene = new Scene(rootNode, 500, 500);
		stage.setTitle("CopperAnt");
		stage.setScene(scene);
//		stage.setMaximized(true);
		stage.show();
	}

	private void helpAbout(Stage stage){
		showPopupMessage("O programie CopperAnt:\nsome text", stage);
	}
	
	private void defaultAction(Stage stage){
		showPopupMessage("default action", stage);
	}
	
	private void helpAuthors(Stage stage){
		showPopupMessage("Autorzy aplikacji: Teleinformatyka CopperAnt's Team", stage);
	}
	public static Popup createPopup(final String message) {
	    final Popup popup = new Popup();
	    popup.setAutoFix(true);
	    popup.setAutoHide(true);
	    popup.setHideOnEscape(true);
	    Label label = new Label(message);
	    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	            popup.hide();
	        }
	    });
	    label.getStylesheets().add("/styles/styles.css");
	    label.getStyleClass().add("popup");
	    popup.getContent().add(label);
	    return popup;
	}

	public static void showPopupMessage(final String message, final Stage stage) {
	    final Popup popup = createPopup(message);
	    popup.setOnShown(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	            popup.setX(stage.getX() + stage.getWidth()/2 - popup.getWidth()/2);
	            popup.setY(stage.getY() + stage.getHeight()/2 - popup.getHeight()/2);
	        }
	    });        
	    popup.show(stage);
	}
}
