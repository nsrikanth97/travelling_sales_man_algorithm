package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;
import javafx.scene.canvas.GraphicsContext;
import org.junit.Test;


import java.awt.*;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MinimumSpanningTreeTest {

    @Test
    public void testLengthOfMST(){
        GraphUsingMatrix graph = new GraphUsingMatrix(5);
        graph.addNode(new Node(40.7128,-74.0060 ));
        graph.addNode(new Node(51.5074,-0.1278));
        graph.addNode(new Node(45.5231,-122.6765));
        graph.addNode(new Node(35.6895,139.6917));
        graph.addNode(new Node(-33.8688,151.2093));
        graph.addAllEdges();
        double mstLength2 = MinimumSpanningTree.generateMST(graph,0,null,null);
        double mstLength = findMSTLengthByBruteForce(graph,false,false);
        assertEquals(mstLength,mstLength2);
    }

    @Test
    public void testMSTPath(){
        GraphUsingMatrix graph = new GraphUsingMatrix(5);
        graph.addNode(new Node(40.7128,-74.0060 ));
        graph.addNode(new Node(51.5074,-0.1278));
        graph.addNode(new Node(45.5231,-122.6765));
        graph.addNode(new Node(35.6895,139.6917));
        graph.addNode(new Node(-33.8688,151.2093));
        graph.addAllEdges();
        MinimumSpanningTree.generateMST(graph,0,null,null);
        findMSTLengthByBruteForce(graph,true,false);

    }

    @Test
    public void testOddDegreeList(){
        GraphUsingMatrix
                graph = new GraphUsingMatrix(9);
        graph.addNode(new Node(40.7128,-74.0060 ));
        graph.addNode(new Node(51.5074,-0.1278));
        graph.addNode(new Node(45.5231,-122.6765));
        graph.addNode(new Node(35.6895,139.6917));
        graph.addNode(new Node(-33.8688,151.2093));
        graph.addNode(new Node(41.8781,-87.6298));
        graph.addNode(new Node(48.8566,2.3522));
        graph.addNode(new Node(-22.9068,-43.1729));
        graph.addNode(new Node(37.7749,-122.4194));
        graph.addNode(new Node(51.5074,-0.1278));
        graph.addAllEdges();

        MinimumSpanningTree.generateMST(graph,0,null,null);
        int count = (int) findMSTLengthByBruteForce(graph,false, true);
        assertEquals(graph.sizeOfOddDegreeNodes(),count);

    }




    public static double findMSTLengthByBruteForce(Graph g, boolean testPath, boolean testOddDegreeCount) {
        int n = g.getSize();
        boolean[] visited = new boolean[n];
        double[] dist = new double[n];
        int[] parent = new int[n];
        int[] degreeArray = new int[n];
        parent[0] = -1;
        int count = 0;

        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[0] = 0;


        double mstLength = 0;
        for (int i = 0; i < n; i++) {
            int minIndex = -1;
            double minDist = Double.POSITIVE_INFINITY;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    minDist = dist[j];
                    minIndex = j;
                }
            }
            visited[minIndex] = true;
            mstLength += minDist;
            if (parent[minIndex] != -1) {
                if(testOddDegreeCount){
                    degreeArray[parent[minIndex]]++;
                    degreeArray[minIndex]++;
                }
                if(testPath)
                    assertTrue(g.getAdjList().get(parent[minIndex]).contains(minIndex));
            }
            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    double d = g.getDistanceBetweenPoints(minIndex,j);
                    if (d < dist[j]) {
                        dist[j] = d;
                        parent[j] = minIndex;
                    }
                }
            }
        }
        if(testOddDegreeCount){
            for(int i=0; i<n;i++){
                if(degreeArray[i]%2 != 0)
                    count++;
            }
            return count;
        }
        return mstLength;
    }
}



