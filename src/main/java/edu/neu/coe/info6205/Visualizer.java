package edu.neu.coe.info6205;

import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import edu.neu.coe.info6205.graph.MinimumSpanningTree;
import edu.neu.coe.info6205.util.ReadDataFromCSV;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Visualizer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println( "Hello World!" );

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        Graph graph = new GraphUsingMatrix( 585);

        ReadDataFromCSV.readData("/info6205.spring2023.teamproject.csv", graph,width, height);
        for(int i= 0; i < graph.getSize() ; i++){
            for(int j=0; j< graph.getSize() ; j++){
                graph.addEdge(i,j);
            }
        }

        double lengthOfMst = MinimumSpanningTree.generateMST(graph, 0);

    }

    public static void main(String[] args) {
        launch();
    }
}
