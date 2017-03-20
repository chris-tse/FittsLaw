package sample;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    final int width = 1000;
    final int height = 600;
    final double radius = 80.0f;
    int count = 0;
    final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
    final ObjectProperty<Point2D> selectedLocation = new SimpleObjectProperty<>();
    String css = this.getClass().getResource("/sample/styles.css").toExternalForm();
    ArrayList<Double> time = new ArrayList();
    double startTime;
    double endTime;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // **********************
        // Start Screen
        // **********************
        Label mainTitle = new Label();
        mainTitle.getStyleClass().add("main-title");
        mainTitle.setText("Fitt's Law Experiment");

        Button startButton = new Button("Start Experiment");
        startButton.getStyleClass().add("menubtn");
        Button exitButton = new Button("Exit Program");
        exitButton.getStyleClass().add("menubtn");


        GridPane layout1 = new GridPane();

        layout1.setHalignment(mainTitle, HPos.CENTER);
        layout1.setHalignment(startButton, HPos.CENTER);
        layout1.setHalignment(exitButton, HPos.CENTER);

        int menuheight = 180;

        layout1.getColumnConstraints().add(new ColumnConstraints(width));
        layout1.getRowConstraints().add(new RowConstraints(menuheight));
        layout1.getRowConstraints().add(new RowConstraints(menuheight));
        layout1.getRowConstraints().add(new RowConstraints(menuheight));
        layout1.setConstraints(mainTitle, 0, 0);
        layout1.setConstraints(startButton, 0, 1);
        layout1.setConstraints(exitButton, 0, 2);
        layout1.getChildren().addAll(mainTitle, startButton, exitButton);

        Scene startScene = new Scene(layout1, width, height);
        startScene.getStylesheets().add(css);
        // **********************
        // Experiment Screen
        // **********************
        Color blue = Color.web("#2196F3");
        final Circle circle = new Circle(radius, blue);
        double startx = 100.0f;
        double starty = 100.0f;
        circle.setCenterX(startx);
        circle.setCenterY(starty);

        double[] xval = {200.0f, 300.0f, 400.0f};
        double[] yval = {200.0f, 300.0f, 400.0f};

        Pane layout2 = new Pane();
        layout2.getChildren().add(circle);

        Scene expScene = new Scene(layout2, width, height);

        // **********************
        // End Graph Screen
        // **********************



        StackPane layout3 = new StackPane();
        //layout3.getChildren().add();

        Scene graphScene = new Scene(layout3, width, height);
        // **********************
        // Event Handlers
        // **********************

        startButton.setOnAction(e -> {
            primaryStage.setScene(expScene);
            startTime = System.currentTimeMillis();
        });
        exitButton.setOnAction(e -> primaryStage.close());

//        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//
//        });


        primaryStage.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            double x = e.getSceneX();
            double y = e.getSceneY();
            double cx = circle.getCenterX();
            double cy = circle.getCenterY();
            double distanceSquared = (x - cx) * (x - cx) + (y - cy) * (y - cy);
            if (primaryStage.getScene().equals(expScene)) {
                if (distanceSquared <= radius*radius) {
                    endTime = System.currentTimeMillis();
                    time.add((endTime - startTime)/1000);
                    if (count < xval.length) {
                        circle.setCenterY(yval[count]);
                        circle.setCenterX(xval[count]);
                        count++;
                    } else {
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
}
