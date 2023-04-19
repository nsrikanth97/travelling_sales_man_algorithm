package edu.neu.coe.info6205;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.*;
import edu.neu.coe.info6205.optimization.RandomSwapping;
//import edu.neu.coe.info6205.optimization.ThreeOpt;
import edu.neu.coe.info6205.*;
import edu.neu.coe.info6205.optimization.SimulatedAnnealing;
import edu.neu.coe.info6205.optimization.ThreeOpt;
import edu.neu.coe.info6205.optimization.TwoOpt;
import edu.neu.coe.info6205.util.ReadDataFromCSV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import java.util.List;


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
        Label label = new Label("Length of MST length (Green): ");
        label.setLayoutX(10); // set X position
        label.setLayoutY(10); // set Y position
        Label label2 = new Label("Christofides  tour length (Black Line): ");
        label2.setLayoutX(10); // set X position
        label2.setLayoutY(30); // set Y positi
        Label label3 = new Label("Percentage difference : ");
        label3.setLayoutX(10); // set X position
        label3.setLayoutY(50); // set Y position
        Button showTwoOptTour = new Button("Show Two Opt Tour");
        showTwoOptTour.setLayoutX(10);
        showTwoOptTour.setLayoutY(70);
        Button showRandomSwappingTour = new Button("Show Random Swap Tour");
        showRandomSwappingTour.setLayoutX(10);
        showRandomSwappingTour.setLayoutY(90);
        Button showThreeOptTour = new Button("Show Three opt Tour");
        showThreeOptTour.setLayoutX(10);
        showThreeOptTour.setLayoutY(110);
        Button showSATour = new Button("Simulated Annealing Tour");
        showSATour.setLayoutX(10);
        showSATour.setLayoutY(130);
        Pane root = new Pane(canvas);
        root.getChildren().add(label);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
        root.getChildren().add(showTwoOptTour);
        root.getChildren().add(showRandomSwappingTour);
        root.getChildren().add(showThreeOptTour);
        root.getChildren().add(showSATour);


        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();


        Graph graph = new GraphUsingMatrix(585);

        ReadDataFromCSV.readData("/info6205.spring2023.teamproject.csv", graph, gc, width, height);
        graph.addAllEdges();
        Timer timer = new Timer(1000, null);
        timer.addActionListener((e) -> {
            showTwoOptTour.setDisable(true);
            showRandomSwappingTour.setDisable(true);
            showThreeOptTour.setDisable(true);
            double lengthOfMst = MinimumSpanningTree.generateMST(graph, 0, gc, label);
            MinimumWeightMatching.findMinimumWeightMatching(graph, gc);
            TspTour tspTour = TravellingSalesPersonTour.findTravellingSalesPersonTour(graph,0,gc,label2);
            double tspTourL = tspTour.getLength();
            Platform.runLater(() -> {
                label3.setText("Percentage difference :" + ((tspTourL / lengthOfMst) - 1) * 100);
            });
            TspTour randomSwapping = RandomSwapping.randomSwapping(tspTour,graph,false);
            System.out.println("Length of Random swapping : " + randomSwapping.getLength()*1000);

            TspTour twoOptTour = TwoOpt.twoOpt(tspTour,graph, false);
            System.out.println("Two opt : " + twoOptTour.getLength());
            TspTour threeOptTour = ThreeOpt.threeOpt(tspTour,graph, false);
            System.out.println("Three opt : " + threeOptTour.getLength());
            TspTour simTour = SimulatedAnnealing.simulatedAnnealing(tspTour,100, 0.99,graph);
            System.out.println("Sim opt : " + simTour.getLength());
            showTwoOptTour.setDisable(false);
            showThreeOptTour.setDisable(false);
            showRandomSwappingTour.setDisable(false);
            showTwoOptTour.setOnAction(event -> {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLACK);
                label2.setText("Length of Two Opt Tour (RED) : " + twoOptTour.getLength()*1000 );
                label3.setText("Percentage difference after TWO-OPT :" + ((twoOptTour.getLength()/lengthOfMst) - 1) * 100);

                for(Node n : graph.getNodeList()){
                    gc.fillOval(n.getX(),n.getY(), 5, 5);
                }
                // Create a new task to draw the graph
                Task<Void> drawGraphTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // Draw the TSP tour using black lines
                        List<Integer> tour = tspTour.getTour();
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }
                        // Draw the two-opt tour using RED color
                        gc.setStroke(Color.RED);
                        tour = twoOptTour.getTour();
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }

                        return null;
                    }
                };
                Thread thread = new Thread(drawGraphTask);
                thread.setDaemon(true);
                thread.start();
            });
            showRandomSwappingTour.setOnAction(event -> {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLACK);
                label2.setText("Length of Random swap Tour (RED) : " + randomSwapping.getLength()*1000 );
                label3.setText("Percentage difference after Random Swap :" + ((randomSwapping.getLength()/lengthOfMst) - 1) * 100);

                for(Node n : graph.getNodeList()){
                    gc.fillOval(n.getX(),n.getY(), 5, 5);
                }
                // Create a new task to draw the graph
                Task<Void> drawGraphTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // Draw the TSP tour using black lines
                        List<Integer> tour = tspTour.getTour();
                        gc.setStroke(Color.BLACK);
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }
                        // Draw the two-opt tour using RED color
                        gc.setStroke(Color.RED);
                        tour = randomSwapping.getTour();
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }

                        return null;
                    }
                };
                Thread thread = new Thread(drawGraphTask);
                thread.setDaemon(true);
                thread.start();
            });
            showSATour.setOnAction(event -> {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLACK);
                label2.setText("Length of Random swap Tour (RED) : " + simTour.getLength()*1000 );
                label3.setText("Percentage difference after Random Swap :" + ((simTour.getLength()/lengthOfMst) - 1) * 100);

                for(Node n : graph.getNodeList()){
                    gc.fillOval(n.getX(),n.getY(), 5, 5);
                }
                // Create a new task to draw the graph
                Task<Void> drawGraphTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // Draw the TSP tour using black lines
                        List<Integer> tour = tspTour.getTour();
                        gc.setStroke(Color.BLACK);
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }
                        // Draw the two-opt tour using RED color
                        gc.setStroke(Color.RED);
                        tour = simTour.getTour();
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }

                        return null;
                    }
                };
                Thread thread = new Thread(drawGraphTask);
                thread.setDaemon(true);
                thread.start();
            });

            showThreeOptTour.setOnAction(event -> {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLACK);
                label2.setText("Length of Three Opt Tour (RED) : " + threeOptTour.getLength()*1000 );
                label3.setText("Percentage difference after Random Swap :" + ((threeOptTour.getLength()/lengthOfMst) - 1) * 100);

                for(Node n : graph.getNodeList()){
                    gc.fillOval(n.getX(),n.getY(), 5, 5);
                }
                // Create a new task to draw the graph
                Task<Void> drawGraphTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        // Draw the TSP tour using black lines
                        List<Integer> tour = tspTour.getTour();
                        gc.setStroke(Color.BLACK);
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }
                        // Draw the two-opt tour using RED color
                        gc.setStroke(Color.RED);
                        tour = threeOptTour.getTour();
                        for(int i=0; i< tour.size()-1;i++){
                            Node start = graph.getNode(tour.get(i));
                            Node end = graph.getNode(tour.get(i+1));
                            Thread.sleep(10);
                            Platform.runLater(()-> {
                                gc.strokeLine(start.getX(),start.getY(),end.getX(), end.getY());
                            });
                        }

                        return null;
                    }
                };
                Thread thread = new Thread(drawGraphTask);
                thread.setDaemon(true);
                thread.start();
            });


            timer.stop();
        });
        timer.start();

    }

    public static void main(String[] args) {
        launch();
    }
}
