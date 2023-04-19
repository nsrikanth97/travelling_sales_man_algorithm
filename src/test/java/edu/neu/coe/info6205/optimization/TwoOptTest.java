package edu.neu.coe.info6205.optimization;

import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TwoOptTest {
    @Test
    public void twoOptSwapTest() {
        List<Integer> tour = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> newTour = TwoOpt.twoOptSwap(tour, 1, 3);
        assertEquals(Arrays.asList(1, 4, 3, 2, 5), newTour);
    }

    @Test
    public void tourLengthTest() {
        GraphUsingMatrix g = new GraphUsingMatrix(5);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3);
        g.addEdge(3, 4, 4);
        g.addEdge(4, 0, 5);
        List<Integer> tour = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        double length = TwoOpt.tourLength(tour, g);
        assertEquals(10, length, 0.001);
    }

    @Test
    public void twoOptTest() {

        List<Integer> newTour = TwoOpt.twoOptSwap(Arrays.asList(0, 1, 2, 3, 4,5,7,9,10,0), 2,6);
        assertEquals(Arrays.asList(0, 1,7,5,4,3,2,9,10,0), newTour);
    }
}
