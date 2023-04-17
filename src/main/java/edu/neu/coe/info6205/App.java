package edu.neu.coe.info6205;

import edu.neu.coe.info6205.entity.Edge;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import edu.neu.coe.info6205.graph.MinimumSpanningTree;
import edu.neu.coe.info6205.util.ReadDataFromCSV;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Graph graph = new GraphUsingMatrix(150);
//        ReadDataFromCSV.readData("/crimesample1.csv", graph);
        for(int i= 0; i < graph.getSize() ; i++){
            for(int j=0; j< graph.getSize() ; j++){
                graph.addEdge(i,j);
            }
        }
        Canvas canvas;
        canvas = new Canvas(600, 600);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        Scene scene = new Scene(canvas.getParent());

//        List<Edge> mst = MinimumSpanningTree.generateMST(graph,0);
        System.out.println(graph.getSize());
    }
}
