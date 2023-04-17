package edu.neu.coe.info6205;

import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import edu.neu.coe.info6205.graph.MinimumSpanningTree;
import edu.neu.coe.info6205.graph.MinimumWeightMatching;
import edu.neu.coe.info6205.util.ReadDataFromCSV;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;


public class Visualizer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Hello World!");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Canvas canvas;
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        canvas = new Canvas(width, height);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        Label label = new Label("Length of MST length : ");
        label.setLayoutX(10); // set X position
        label.setLayoutY(10); // set Y position
        Label label2 = new Label("Length of TSP tour : ");
        label2.setLayoutX(10); // set X position
        label2.setLayoutY(30); // set Y positi
        Label label3 = new Label("Percentage difference : ");
        label3.setLayoutX(10); // set X position
        label3.setLayoutY(50); // set Y position
        Button newButton = new Button("View TSP Path");
        newButton.setLayoutX(10);
        newButton.setLayoutY(70);
        Pane root = new Pane(canvas);
        root.getChildren().add(label);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
        root.getChildren().add(newButton);

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();


        Graph graph = new GraphUsingMatrix(585);

        ReadDataFromCSV.readData("/info6205.spring2023.teamproject.csv", graph, gc, width, height);
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = 0; j < graph.getSize(); j++) {
                graph.addEdge(i, j);
            }
        }
        Timer timer = new Timer(1000, null);
        timer.addActionListener((e) -> {
            double lengthOfMstt = MinimumSpanningTree.generateMST(graph, 0, gc, label);
            MinimumWeightMatching.findMinimumWeightMatching(graph);
            timer.stop();
        });
        timer.start();

    }

    public static void main(String[] args) {
        launch();
    }
}
