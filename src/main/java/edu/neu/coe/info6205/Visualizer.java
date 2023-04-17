package edu.neu.coe.info6205;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.*;
import edu.neu.coe.info6205.optimization.AntColonyOptimization;
import edu.neu.coe.info6205.optimization.RandomSwapping;
import edu.neu.coe.info6205.optimization.ThreeOpt;
import edu.neu.coe.info6205.optimization.TwoOpt;
import edu.neu.coe.info6205.util.ReadDataFromCSV;
import javafx.application.Application;
import javafx.application.Platform;
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
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedPerfectMatching;
import org.jgrapht.alg.matching.blossom.v5.ObjectiveSense;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import javax.swing.*;
import java.util.List;

public class Visualizer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println( "Hello World!" );

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
        Button newButton = new Button("View TSP Path" );
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
        Graph graph = new GraphUsingMatrix( 585);

        ReadDataFromCSV.readData("/info6205.spring2023.teamproject.csv", graph,gc, width, height);
        for(int i= 0; i < graph.getSize() ; i++){
            for(int j=0; j< graph.getSize() ; j++){
                graph.addEdge(i,j);
            }
        }
        Timer timer = new Timer(1000, null);
        timer.addActionListener((e) -> {
            double lengthOfMst = MinimumSpanningTree.generateMST(graph, 0, gc, label);
//            SimpleWeightedGraph<Integer, DefaultWeightedEdge>  g= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//            for(Node node: graph.getDegreeGreaterList()){
//                g.addVertex(node.getPos());
//            }
//            int sPoint;
//            int ePoint;
//            for(int i=0; i< graph.getDegreeGreaterList().size();i++){
//                for(int j=0; j< graph.getDegreeGreaterList().size();j++){
//                    if(i ==j )
//                        continue;
//                    sPoint = graph.getDegreeGreaterList().get(i).getPos();
//                    ePoint = graph.getDegreeGreaterList().get(j).getPos();
//                    g.addEdge(sPoint, ePoint);
//                    g.setEdgeWeight(g.getEdge(sPoint,ePoint),graph.getDistanceBetweenPoints(sPoint,ePoint));
//                }
//            }
//            KolmogorovWeightedPerfectMatching<Integer, DefaultWeightedEdge> matching = new KolmogorovWeightedPerfectMatching(g, ObjectiveSense.MINIMIZE);
//            MatchingAlgorithm.Matching<Integer, DefaultWeightedEdge> s =  matching.getMatching();
//            List<List<Integer>> adjList = graph.getAdjList();
//            for(DefaultWeightedEdge edg : s.getEdges()){
//                adjList.get(g.getEdgeSource(edg)).add(g.getEdgeTarget(edg));
//                adjList.get(g.getEdgeTarget(edg)).add(g.getEdgeSource(edg));
//            }
//            graph.setAdjList(adjList);
            MinimumWeightMatching.findMinimumWeightMatching(graph.getDegreeGreaterList(),graph,gc);
            TspTour tspTour = EulerianTour.findEulerianTour(graph,0,gc,label2);
            double tspTourL = tspTour.getLength();

//            TspTour tspTourRandomSwapping = RandomSwapping.randomSwapping(tspTour,graph);
//            Platform.runLater(() -> {
//                label3.setText("Percentage difference :" + ((tspTourL / lengthOfMst) - 1) * 100);
//            });
//            System.out.println("Random Swap : " + tspTourRandomSwapping.getLength());
//            TspTour twoOptTour = TwoOpt.twoOpt(tspTour,graph);
//            System.out.println("Two opt : " + twoOptTour.getLength());
//            TspTour threeOptTour = ThreeOpt.threeOpt(tspTour,graph);
//            System.out.println("Three opt : " + threeOptTour.getLength());
            AntColonyOptimization antColonyOptimization = new AntColonyOptimization(3,1000,graph,tspTour.getTour() );
            antColonyOptimization.run();
//            TspTour simulatedAnnealing = SimulatedAnnealing.simulatedAnnealing(tspTour,2,0.95,graph);
//            System.out.println(simulatedAnnealing.getLength());
//            newButton.setOnAction(event -> {
//                Stage modalStage = new Stage();
//                modalStage.initOwner(stage);
//                modalStage.initModality(Modality.APPLICATION_MODAL);
//
//                Label modalLabel = new Label("This is a modal");
//                Button closeModalButton = new Button("Close");
//                closeModalButton.setOnAction(closeEvent -> modalStage.close());
//
//                VBox modalBox = new VBox(modalLabel, closeModalButton);
//                modalBox.setPadding(new Insets(10));
//                modalBox.setSpacing(10);
//
//                Scene modalScene = new Scene(modalBox);
//                modalStage.setScene(modalScene);
//                modalStage.showAndWait();
//            });
            timer.stop();
        });
        timer.start();
        System.out.println(graph.getSize());
    }

    public static void main(String[] args) {
        launch();
    }
}
