package sample;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends Application {
    final static int width = 1000;
    final static int height = 800;
    final static double radius = 30.0f;

    int iterations = 10;
    int count = 0;

    String css = this.getClass().getResource("/sample/styles.css").toExternalForm();

    ArrayList<Double> times = new ArrayList();
    ArrayList<Double> dists = new ArrayList();

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
        GridPane.setConstraints(mainTitle, 0, 0);
        GridPane.setHalignment(mainTitle, HPos.CENTER);
        GridPane.setValignment(mainTitle, VPos.CENTER);

        Button startButton = new Button("Start Experiment");
        startButton.getStyleClass().add("menubtn");
        GridPane.setConstraints(startButton, 0, 1);
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setValignment(startButton, VPos.CENTER);

        Button exitButton = new Button("Exit Program");
        exitButton.getStyleClass().add("menubtn");
        GridPane.setConstraints(exitButton, 0, 2);
        GridPane.setHalignment(exitButton, HPos.CENTER);
        GridPane.setValignment(exitButton, VPos.CENTER);


        layout1.getChildren().addAll(mainTitle, startButton, exitButton);

        Scene startScene = new Scene(layout1, width, height);
        startScene.getStylesheets().add(css);



        // **********************
        // Instructions Screen
        // **********************
        GridPane layout2 = new GridPane();
        layout2.setPadding(new Insets(10, 20, 50, 20));
        layout2.setVgap(10);
        layout2.setHgap(30);
        layout2.getRowConstraints().add(rowConstraints);
        layout2.getColumnConstraints().add(columnConstraints);

        Label instructTitle = new Label();
        instructTitle.getStyleClass().add("instructions-title");
        instructTitle.setText("Instructions");
        instructTitle.setWrapText(true);
        instructTitle.setTextAlignment(TextAlignment.CENTER);
        GridPane.setConstraints(instructTitle, 0, 0);
        GridPane.setHalignment(instructTitle, HPos.CENTER);
        GridPane.setValignment(instructTitle, VPos.CENTER);

        Label instruct = new Label();
        instruct.getStyleClass().add("instructions");
        instruct.setText("Click the circle each time it moves to a new position as quickly and accurately as you can. " +
                "You can set the number of iterations in the spinner. The recommended default is 10 times. " +
                "The test will begin as soon as you click the circle below");
        instruct.setWrapText(true);
        instruct.setTextAlignment(TextAlignment.CENTER);
        GridPane.setConstraints(instruct, 0, 1);
        GridPane.setHalignment(instruct, HPos.CENTER);
        GridPane.setValignment(instruct, VPos.CENTER);

        Spinner<Integer> spinner = new Spinner(5, 30, 10);
        GridPane.setConstraints(spinner, 0, 2);
        GridPane.setHalignment(spinner, HPos.CENTER);
        GridPane.setValignment(spinner, VPos.CENTER);

        Color blue = Color.web("#2196F3");
        final Circle startCircle = new Circle(radius, blue);
        GridPane.setConstraints(startCircle, 0, 3);
        GridPane.setHalignment(startCircle, HPos.CENTER);
        GridPane.setValignment(startCircle, VPos.CENTER);

        layout2.getChildren().addAll(instructTitle, instruct, spinner, startCircle);
        Scene instructScene = new Scene(layout2, width, height);
        instructScene.getStylesheets().add(css);

        // **********************
        // Experiment Screen
        // **********************

        final Circle expCircle = new Circle(radius, blue);
        double circleStartX = getRandom(width-(radius+20)); //min radius+20 max width-100
        double circleStartY = getRandom(height-(radius+20)); //min radius+20 max height-100
        expCircle.setCenterX(circleStartX);
        expCircle.setCenterY(circleStartY);

        double[] xval = {100.0f, 500.0f, 700.0f};
        double[] yval = {200.0f, 200.0f, 200.0f};


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

        });

        exitButton.setOnAction(e -> primaryStage.close());

        startCircle.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            startXPos = startCircle.getCenterX();
            startYPos = startCircle.getCenterY();
            primaryStage.setScene(expScene);
            startTime = System.currentTimeMillis();
            iterations = spinner.getValue();
        });
//        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//
//        });


        expScene.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            double x = e.getSceneX();
            double y = e.getSceneY();
            double cx = expCircle.getCenterX();
            double cy = expCircle.getCenterY();
            double distanceSquared = (x - cx) * (x - cx) + (y - cy) * (y - cy);

            if (distanceSquared <= radius*radius) {
                endTime = System.currentTimeMillis();
                double time = (endTime - startTime);
                times.add(time);
                double dist = Math.sqrt(Math.pow(Math.abs(cx - startXPos), 2) + Math.pow(Math.abs(cy - startYPos), 2));
                dists.add(dist);
                count++;
                if (count < iterations) {
                    expCircle.setCenterY(getRandom(height-(radius+20)));
                    expCircle.setCenterX(getRandom(width-(radius+20)));
                    startXPos = x;
                    startYPos = y;
                    startTime = System.currentTimeMillis();
                } else {
                    times.forEach(num -> System.out.println(num));
                    dists.forEach(num -> System.out.println(num));
                    buildGraph(times, dists, (Stage) ((Scene) e.getSource()).getWindow());
                }

            } else {
                System.out.println("mouse click outside " + e.getSceneX());
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

    private static void buildGraph(ArrayList<Double> times, ArrayList<Double> dists, Stage stage) {
        double[] moveArr = new double[times.size()];
        double[] diffArr = new double[dists.size()];

        for(int i = 0; i < times.size(); i++) {
            moveArr[i] = times.get(i).doubleValue();
            diffArr[i] = Math.log((dists.get(i).doubleValue()/(radius/2)) + 1);
        }

        double maxDiff = getMax(diffArr);
        double maxMove = getMax(moveArr);

        double minDiff = getMin(diffArr);
        double minMove = getMin(moveArr);

        final NumberAxis xAxis = new NumberAxis(0, maxDiff, maxDiff/10);
        final NumberAxis yAxis = new NumberAxis(0, 1000, maxMove/10);

        final ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel("Index of Difficulty");
        yAxis.setLabel("Movement Time (ms)");

        sc.setTitle("Fitt's Law Relation");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Click Time vs Index of Difficulty");

        for(int i = 0; i < diffArr.length; i++) {
            series1.getData().add(new XYChart.Data(diffArr[i], moveArr[i]));
        }

        sc.getData().add(series1);

        Scene graphScene  = new Scene(sc, width, height);

        stage.setScene(graphScene);

    }

    private static double getMax(double[] arr) {
        double max = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
        }
        return max;
    }

    private static double getMin(double[] arr) {
        double min = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < min)
                min = arr[i];
        }
        return min;
    }
}
