package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GuiJankenGame extends Application {
	public void start(Stage stage) throws Exception {
		String title = "Janken";
		GridPane root = FXMLLoader.load(getClass().getResource(title + ".fxml"));

		stage.setScene(new Scene(root));
		stage.setTitle(title);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
