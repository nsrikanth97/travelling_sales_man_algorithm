package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class GraphUsingMatrixTest  {



    @Test
    public void testConstructor(){
        Graph graph = new GraphUsingMatrix(150);

        assertEquals(150, graph.getAdjList().size());
        assertEquals(150, graph.getAdjMatrix().length);
        assertEquals(150, graph.getAdjMatrix()[0].length);
    }

    @Test
    public void testAddAllEdges(){
        GraphUsingMatrix graph = new GraphUsingMatrix(5);

        graph.addNode(new Node(40.7128,-74.0060 ));
        graph.addNode(new Node(51.5074,-0.1278));
        graph.addNode(new Node(45.5231,-122.6765));
        graph.addNode(new Node(35.6895,139.6917));
        graph.addNode(new Node(-33.8688,151.2093));
        graph.addAllEdges();
        double[][] adjMatrix = graph.getAdjMatrix();
        for(int i=0; i<5;i++){
            for(int j=0; j<5;j++){
                if(i ==j)
                    assertEquals(0.0, adjMatrix[i][j]);
                else
                    assertEquals(adjMatrix[i][j], graph.getDistance(i,j));
            }
        }


    }

    @Test
    public void testDistanceCalculation(){
        GraphUsingMatrix graph = new GraphUsingMatrix(1);

        graph.addNode(new Node(40.7128,-74.0060 ));
        graph.addNode(new Node(51.5074,-0.1278));

        assertEquals(5570, (int) graph.getDistance(0,1));


    }
}
