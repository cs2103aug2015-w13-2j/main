package sg.edu.cs2103aug2015_w13_2j.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class GUI extends Application {
	private WebView browser;
	private WebEngine webEngine;
	
	public void executeCommand(String x) {
	    // TODO
	    System.out.println(x);
	}
	
	private void initializeGUI() {
		// Allows Java upcalls from Javascript
		JSObject window = (JSObject) js("window");
		window.setMember("GUI", new GUI());
		
		js("document.getElementById('commandForm').setAttribute('onsubmit', 'GUI.executeCommand(this.command.value)')");
	}
	
	// Convenience method to run Javascript commands
	private Object js(String command) {
		return webEngine.executeScript(command);
	}
	
	@Override
	public void start(Stage stage) {
		try {
			browser = new WebView();
			webEngine = browser.getEngine();
			webEngine.load(getClass().getResource("GUI.html").toExternalForm());
			
			webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>(){
					public void changed(ObservableValue ov, State oldState, State newState) {
						if (newState == State.SUCCEEDED) {
							initializeGUI();
						}
					}
				}
			);
			
			// TODO: app icon and title in taskbar
		    browser.setContextMenuEnabled(false);
		    Scene scene = new Scene(browser);
		    stage.setTitle("FunDUE");
		    stage.getIcons().add(new Image("file:FunDUE Logo.png"));
		    stage.setHeight(800);
		    stage.setWidth(700);
		    stage.setResizable(false);
		    stage.setScene(scene);
		    stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
