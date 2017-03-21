package sample;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    final static int width = 1000;
    final static int height = 800;
    final static double radius = 80.0f;

    int count = 0;

    String css = this.getClass().getResource("/sample/styles.css").toExternalForm();

    ArrayList<Double> time = new ArrayList();

    double startTime;
    double endTime;
    double startXPos;
    double startYPos;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // **********************
        // Start Screen
        // **********************
        GridPane layout1 = new GridPane();
        layout1.setPadding(new Insets(10, 10, 50, 10));
        layout1.setVgap(10);
        layout1.setHgap(100);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        layout1.getColumnConstraints().add(columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setFillHeight(true);
        rowConstraints.setVgrow(Priority.ALWAYS);
        layout1.getRowConstraints().add(rowConstraints);

        Label mainTitle = new Label();
        mainTitle.getStyleClass().add("main-title");
        mainTitle.setText("Fitt's Law Experiment");
        layout1.setConstraints(mainTitle, 0, 0);

        Button startButton = new Button("Start Experiment");
        startButton.getStyleClass().add("menubtn");
        layout1.setConstraints(startButton, 0, 1);

        Button exitButton = new Button("Exit Program");
        exitButton.getStyleClass().add("menubtn");
        layout1.setConstraints(exitButton, 0, 2);


        // Horizontal and Vertical Alignment of items
        layout1.setHalignment(mainTitle, HPos.CENTER);
        layout1.setHalignment(startButton, HPos.CENTER);
        layout1.setHalignment(exitButton, HPos.CENTER);

        layout1.setValignment(mainTitle, VPos.CENTER);
        layout1.setValignment(startButton, VPos.CENTER);
        layout1.setValignment(exitButton, VPos.CENTER);

        layout1.getChildren().addAll(mainTitle, startButton, exitButton);

        Scene startScene = new Scene(layout1, width, height);
        startScene.getStylesheets().add(css);
        // **********************
        // Instructions Screen
        // **********************
        GridPane layout2 = new GridPane();
        layout2.setPadding(new Insets(10, 10, 50, 10));
        layout2.setVgap(10);
        layout2.setHgap(100);

        Label instruct = new Label();
        instruct.getStyleClass().add("instructions");
        instruct.setText("Lorem ipsum dolor si amet. The test will begin as soon as you click the circle below");
        layout2.setConstraints(instruct, 0, 0);

        Spinner<Integer> spinner = new Spinner(5, 30, 10);
        layout2.setConstraints(spinner, 0, 1);

        Color blue = Color.web("#2196F3");
        final Circle startCircle = new Circle(radius, blue);

        layout2.getChildren().addAll(instruct, spinner, startCircle);
        Scene instructScene = new Scene(layout2, width, height);
        instructScene.getStylesheets().add(css);

        // **********************
        // Experiment Screen
        // **********************
        int iterations = spinner.getValue();
        final Circle expCircle = new Circle(radius, blue);
        double circleStartX = getRandom(width-(radius+20)); //min radius+20 max width-100
        double circleStartY = getRandom(height-(radius+20)); //min radius+20 max height-100
        expCircle.setCenterX(circleStartX);
        expCircle.setCenterY(circleStartY);

        double[] xval = {200.0f, 300.0f, 400.0f};
        double[] yval = {200.0f, 300.0f, 400.0f};


        Pane layout3 = new Pane();
        layout3.getChildren().add(expCircle);

        Scene expScene = new Scene(layout3, width, height);

        // **********************
        // End Graph Screen
        // **********************



        StackPane layout4 = new StackPane();
        //layout4.getChildren().add();

        Scene graphScene = new Scene(layout4, width, height);


        // **********************
        // Event Handlers
        // **********************

        startButton.setOnAction(e -> {
            primaryStage.setScene(instructScene);
            startTime = System.currentTimeMillis();
        });

        exitButton.setOnAction(e -> primaryStage.close());

        startCircle.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            startXPos = startCircle.getCenterX();
            startYPos = startCircle.getCenterY();
            primaryStage.setScene(expScene);
        });
//        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//
//        });


        primaryStage.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            double x = e.getSceneX();
            double y = e.getSceneY();
            double cx = expCircle.getCenterX();
            double cy = expCircle.getCenterY();
            double distanceSquared = (x - cx) * (x - cx) + (y - cy) * (y - cy);
            if (primaryStage.getScene().equals(expScene)) {
                if (distanceSquared <= radius*radius) {
                    endTime = System.currentTimeMillis();
                    time.add((endTime - startTime)/1000);
                    count++;
                    if (count < xval.length) {
                        expCircle.setCenterY(getRandom(height-(radius-20)));
                        expCircle.setCenterX(getRandom(width-(radius-20)));

                    } else {
                        time.forEach(num -> System.out.println(num));
                        primaryStage.setScene(graphScene);
                    }

                } else {
                    System.out.println("mouse click outside " + e.getSceneX());
                }
            }
        });
        primaryStage.setTitle("Fitt's Law Experiment");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }

    private static double getRandom(double max) {
        Random r = new Random();
        return (radius+20) + (max - (radius+20)) * r.nextDouble();
    }
}
