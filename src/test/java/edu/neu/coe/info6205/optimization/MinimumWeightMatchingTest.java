package edu.neu.coe.info6205.optimization;
import edu.neu.coe.info6205.entity.Edge;
import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import edu.neu.coe.info6205.graph.MinimumWeightMatching;
import javafx.scene.canvas.GraphicsContext;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MinimumWeightMatchingTest {

    private static List<Node> nodes;
    private static GraphUsingMatrix graph;
    private static GraphicsContext gc;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Create nodes and graph for testing
        double adjMatrix[][]=new double[6][6];
        for(int i=0 ;i < 6; i++){
            for(int j = 0 ; j<6; j++){
                adjMatrix[i][j] = i+j;
            }
        }
        nodes = new ArrayList<>();
        nodes.add(new Node(null, 0, 0, 0, 0));
        nodes.get(0).setPos(0);
        nodes.add(new Node(null, 1, 1, 1, 0));
        nodes.get(1).setPos(1);
        nodes.add(new Node(null, 2, 2, 2, 0));
        nodes.get(2).setPos(2);
        nodes.add(new Node(null, 3, 3, 3, 0));
        nodes.get(3).setPos(3);
        nodes.add(new Node(null, 4, 4, 4, 0));
        nodes.get(4).setPos(4);
        nodes.add(new Node(null, 5, 5, 5, 0));
        nodes.get(5).setPos(5);
        graph = new GraphUsingMatrix(nodes.size());
        graph.setOddDegreeNodes(nodes);
        for(Node node : nodes)
        graph.addNode(node);
        graph.setAdjMatrix(adjMatrix);
        //gc = new JFXPanel().getGraphicsContext2D();
    }

    @Test
    public void testFindMinimumWeightMatching() {
        List<Edge> matching = MinimumWeightMatching.findMinimumWeightMatching(graph, null);
        assertNotNull(matching);
        assertEquals(3, matching.size());
    }

    @Test
    public void testFindMinimumWeightMatchingWithNoNodes() {
        GraphUsingMatrix emptyGraph = new GraphUsingMatrix(4);
        List<Edge> matching = MinimumWeightMatching.findMinimumWeightMatching(emptyGraph, null);
        assertNotNull(matching);
        assertTrue(matching.isEmpty());
    }

    @Test
    public void testFindMinimumWeightMatchingWithOneNode() {
        List<Node> oneNodeList = new ArrayList<>();
        oneNodeList.add(new Node(null, 0, 0, 0, 0));
        GraphUsingMatrix oneNodeGraph = new GraphUsingMatrix(4);
        List<Edge> matching = MinimumWeightMatching.findMinimumWeightMatching(oneNodeGraph, null);
        assertNotNull(matching);
        assertTrue(matching.isEmpty());
    }

    @Test
    public void testFindMinimumWeightMatchingWithSixNodes() {

        List<Edge> matching = MinimumWeightMatching.findMinimumWeightMatching(graph, null);
        assertEquals(3, matching.size());
        assertEquals(1.0,matching.get(0).getWeight(),0.1);
        assertEquals(5.0,matching.get(1).getWeight(),0.1);
    }
}
