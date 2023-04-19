package edu.neu.coe.info6205.optimization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.GraphUsingMatrix;
import edu.neu.coe.info6205.optimization.RandomSwapping;

public class RandomSwappingTest {

    @Test
    public void testRandomSwapping() {
        // create a graph
        GraphUsingMatrix g = new GraphUsingMatrix(4);
        g.setDistanceBetweenPoints(0, 1, 1);
        g.setDistanceBetweenPoints(0, 2, 3);
        g.setDistanceBetweenPoints(0, 3, 2);
        g.setDistanceBetweenPoints(1, 2, 1);
        g.setDistanceBetweenPoints(1, 3, 4);
        g.setDistanceBetweenPoints(2, 3, 1);

        // create a TspTour
        TspTour tspTour = new TspTour();
        tspTour.setTour(Arrays.asList(0, 1, 2, 3));
        tspTour.setLength(6.0);

        // apply random swapping
        TspTour tspTour1 = RandomSwapping.randomSwapping(tspTour, g,true);

        // check if the length is decreased
        assertTrue(tspTour1.getLength() < tspTour.getLength());

        // check if the tour length is correct
        assertEquals(3.0, tspTour1.getLength(), 0.001);

        // check if the tour is a permutation of the original tour
        List<Integer> tourList = tspTour1.getTour();
        List<Integer> originalList = new ArrayList<>(tspTour.getTour());
        originalList.remove(originalList.size() - 1); // remove last element
        originalList.remove(0); // remove first element
        assertEquals(4, tourList.size());

    }

}