package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Main extends Application {
    final int width = 500;
    final int height = 450;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // **********************
        // Start Screen
        // **********************
        Label mainTitle = new Label();
        mainTitle.setText("Fitt's Law Experiment");

        Button startButton = new Button("Start Experiment");
        Button exitButton = new Button("Exit Program");

        GridPane layout1 = new GridPane();

        layout1.setHalignment(mainTitle, HPos.CENTER);
        layout1.setHalignment(startButton, HPos.CENTER);
        layout1.setHalignment(exitButton, HPos.CENTER);

        layout1.getColumnConstraints().add(new ColumnConstraints(500));
        layout1.getRowConstraints().add(new RowConstraints(150));
        layout1.getRowConstraints().add(new RowConstraints(150));
        layout1.getRowConstraints().add(new RowConstraints(150));
        layout1.setConstraints(mainTitle, 0, 0);
        layout1.setConstraints(startButton, 0, 1);
        layout1.setConstraints(exitButton, 0, 2);
        layout1.getChildren().addAll(mainTitle, startButton, exitButton);

        Scene startScene = new Scene(layout1, width, height);
        // **********************
        // Experiment Screen
        // **********************

        Scene expScene = new Scene(layout2, width, height);

        // **********************
        // End Graph Screen
        // **********************


        startButton.setOnAction(e -> primaryStage.setScene(expScene));
        exitButton.setOnAction(e -> primaryStage.close());
        primaryStage.setTitle("Fitt's Law Experiment");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
