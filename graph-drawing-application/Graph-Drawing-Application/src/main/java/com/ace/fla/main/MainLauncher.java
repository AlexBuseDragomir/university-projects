package com.ace.fla.main;

import com.ace.fla.utility.DrawerUtility;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainLauncher extends Application {

    private DrawerUtility drawerUtility;

    {
        this.drawerUtility = new DrawerUtility();
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = this.drawerUtility.makeCanvas();
        this.drawerUtility.setCanvas(canvas);
        this.drawerUtility.paintCanvas();
        StackPane stackPane = new StackPane(canvas);
        stackPane.setStyle("-fx-border-width: 2px; -fx-border-color: #444");
        BorderPane root = new BorderPane(stackPane);
        root.setStyle("-fx-border-width: 1px; -fx-border-color: black");
        root.setBottom(drawerUtility.makeToolPanel());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Graph drawing, made by Alexandru Buse-Dragomir");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
