package pl.edu.pk.iti.copperAnt;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import  as NodeXml;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pl.edu.pk.iti.copperAnt.gui.SimulationCanvas;


public class MainApp extends Application {

	private static final Logger dev_log = LoggerFactory.getLogger("dev_logs");

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	public void start(Stage stage) throws Exception {
		dev_log.info("Starting Hello JavaFX and Maven demonstration application");
                
		String fxmlFile = "/fxml/main.fxml";
		dev_log.debug("Loading FXML for main view from: {}", fxmlFile);
		FXMLLoader loader = new FXMLLoader();
		BorderPane rootNode = (BorderPane) loader.load(getClass().getResourceAsStream(
				fxmlFile));


		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(400, 400);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setContent(new SimulationCanvas());
		
		rootNode.setCenter(scrollPane);
		
		new MenuController(stage,scrollPane,rootNode);
		
		

		dev_log.debug("Showing JFX scene");
		Scene scene = new Scene(rootNode, 500, 500);
		stage.setTitle("CopperAnt");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
	
}
