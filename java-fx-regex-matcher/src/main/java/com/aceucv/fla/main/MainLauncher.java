package com.aceucv.fla.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        @SuppressWarnings("ConstantConditions")
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("layout.fxml"));
        primaryStage.setTitle("Regex Matcher App");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
