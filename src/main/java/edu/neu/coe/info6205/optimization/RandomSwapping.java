package edu.neu.coe.info6205.optimization;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.util.WriteDataToCSV;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSwapping {


    public static TspTour randomSwapping(TspTour tspTour, Graph g, boolean testCase) {
        long timeStart = System.currentTimeMillis();
        System.out.println();
        StringBuilder sb ;

        System.out.printf("Random swapping optimization started at %d", timeStart);
        int n = tspTour.getTour().size();
        double currentLength = tspTour.getLength();
        double bestLength = currentLength;
        List<Integer> currentTour = new ArrayList<>(tspTour.getTour());
        boolean  improved  =true;

        while(improved){
            improved = false;
            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    swap(currentTour,i, j);
                    double newLength = tourLength(currentTour, g);
                    if (newLength < bestLength) {
                        bestLength = newLength;
                        improved = true;
                    }else{
                        swap(currentTour,i, j);
                    }
                }
            }
        }
    if(!testCase){
        BufferedWriter bw = WriteDataToCSV.createBufferedWriter("random_swap_path.csv");
        for(int i = 0; i < currentTour.size()-1;i++){
            Node start = g.getNode(currentTour.get(i));
            Node end = g.getNode(currentTour.get(i+1));
            sb = new StringBuilder("");
            sb.append(i+1).append(",");
            sb.append(start.getPos()).append("(").append(start.getName()).append("),");
            sb.append(start.getLat()).append(",").append(start.getLong()).append(",");
            sb.append(end.getPos()).append("(").append(end.getName()).append("),");
            sb.append(end.getLat()).append(",").append(end.getLong()).append(",");
            sb.append(g.getDistanceBetweenPoints(start.getPos(),end.getPos())*1000);
            WriteDataToCSV.writeData(sb.toString(), bw);
        }
        WriteDataToCSV.closeStream(bw);
        System.out.println();
        System.out.printf("Length of tour after random swap : %f ",bestLength*1000);
        System.out.println();
        System.out.println("Details of the tour generated after two opt optimization can be found in the random_swap_path.csv file");
        long timeEnd = System.currentTimeMillis();
        System.out.printf("Tour generation after random swap optimization Successfully completed  at %d(ms)", timeEnd);
        System.out.println();

        System.out.printf("Time taken for Random swap Optimization :  %d (ms)", timeEnd-timeStart);
    }



        // Return the best tour found
        return new TspTour(currentTour, bestLength);
    }

    public static double tourLength(List<Integer> tour, Graph g) {
        double length = 0;
        int n = tour.size();
        for (int i = 0; i < n - 1; i++) {
            length += g.getDistanceBetweenPoints(tour.get(i),tour.get(i+1));
        }
        return length;
    }

    private static void swap(List<Integer>  tour, int i, int j){
        int temp = tour.get(i);
        tour.set(i, tour.get(j));
        tour.set(j, temp);
    }
}
