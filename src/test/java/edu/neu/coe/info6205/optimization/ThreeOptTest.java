package edu.neu.coe.info6205.optimization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import edu.neu.coe.info6205.optimization.ThreeOpt;


public class ThreeOptTest {

    @Test
    public void threeOpt() {
        GraphUsingMatrix g = new GraphUsingMatrix(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 2);
        g.addEdge(0, 3, 3);
        g.addEdge(1, 2, 4);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 6);

        TspTour tspTour = new TspTour(Arrays.asList(0, 1, 2, 3,0), 15);
        TspTour expected = new TspTour(Arrays.asList(0, 2, 1, 3,0), 12);

        TspTour result = ThreeOpt.threeOpt(tspTour, g,true);

        assertEquals(expected, result);
    }

    @Test
    public void getNewTour() {
        List<Integer> tour = new ArrayList<>(Arrays.asList(0, 1, 2, 4,5 , 6,  7,8, 9, 0));
        List<Integer> expected = new ArrayList<>(Arrays.asList(0, 1, 5, 4,2,8,7,6,9,0));

        List<Integer> result = ThreeOpt.getNewTour(tour, 1, 4, 7);

        assertEquals(expected, result);
    }

    @Test
    public void tourLength() {
        GraphUsingMatrix g = new GraphUsingMatrix(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 2);
        g.addEdge(0, 3, 3);
        g.addEdge(1, 2, 4);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 6);

        List<Integer> tour = new ArrayList<>(Arrays.asList(0, 1, 2, 3,0));
        double expected = 14;

        double result = ThreeOpt.tourLength(tour, g);

        assertEquals(expected, result, 0.001);
    }


}
