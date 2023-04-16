package fr.ul.miage.structu.applastfm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConnexionIHM extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("AppLastFM");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/AppLastFM.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
