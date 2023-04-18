package edu.neu.coe.info6205.optimization;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.util.WriteDataToCSV;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class TwoOpt {

    public static TspTour twoOpt(TspTour tspTour, Graph g) {
        long timeStart = System.currentTimeMillis();
        System.out.println();
        System.out.printf("Two opt optimization started at %d", timeStart);
        boolean improved = true;
        List<Integer> tour = new ArrayList<>(tspTour.getTour());
        int n = tour.size();
        StringBuilder sb ;
        double bestLength = tspTour.getLength();
        while (improved) {
            improved = false;
            for (int i = 1; i < n - 3; i++) {
                for (int j = i + 1; j < n-2; j++) {
                    if (i == j - 1 || i == j) {
                        continue;
                    }
                    List<Integer> newTour = twoOptSwap(tour, i, j);
                    double newLength = tourLength(newTour, g);
                    if (newLength < bestLength) {
                        bestLength = newLength;
                        improved = true;
                        tour = newTour;
                    }
                }
            }
        }

        BufferedWriter bw = WriteDataToCSV.createBufferedWriter("two_opt__path.csv");
        for(int i = 0; i < tour.size()-1;i++){
            Node start = g.getNode(tour.get(i));
            Node end = g.getNode(tour.get(i+1));
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
        System.out.printf("Length of tour after two opt : %f ",bestLength*1000);
        System.out.println();
        System.out.println("Details of the tour generated after two opt optimization can be found in the two_opt__path.csv file");
        long timeEnd = System.currentTimeMillis();
        System.out.printf("Two opt  generation Successfully completed  at %d(ms)", timeEnd);
        System.out.println();

        System.out.printf("Time taken for Two Opt Optimization :  %d (ms)", timeEnd-timeStart);

        return new TspTour(tour, bestLength);
    }

    public static List<Integer> twoOptSwap(List<Integer> tour, int i, int j) {
        int n = tour.size();
        List<Integer> newTour = new ArrayList<>();
        for (int k = 0; k <= i - 1; k++) {
            newTour.add(tour.get(k));
        }
        for (int k = j; k >= i; k--) {
            newTour.add(tour.get(k));
        }
        for (int k = j + 1; k < n; k++) {
            newTour.add(tour.get(k));
        }
        return newTour;
    }

    public static double tourLength(List<Integer> tour, Graph g) {
        double length = 0;
        int n = tour.size();
        for (int i = 0; i < n - 1; i++) {
            length += g.getDistanceBetweenPoints(tour.get(i),tour.get(i+1));
        }
        return length;
    }


}
