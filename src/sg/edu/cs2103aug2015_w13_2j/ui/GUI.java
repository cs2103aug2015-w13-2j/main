package sg.edu.cs2103aug2015_w13_2j.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
		Scene scene = new Scene(root);
		
		// TODO: app icons in window and in taskbar
		stage.setTitle("FunDUE");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
