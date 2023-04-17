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

    }

    public static void main(String[] args) {
        launch();
    }
}
