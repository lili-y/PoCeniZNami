package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Turistična agencija Poletite v neznano");
        stage.setScene(scene);

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}