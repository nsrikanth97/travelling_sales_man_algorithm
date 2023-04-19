package edu.neu.coe.info6205.optimization;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.util.WriteDataToCSV;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class ThreeOpt {
    public static TspTour threeOpt(TspTour tspTour, Graph g, boolean testCase) {
        long timeStart = System.currentTimeMillis();
        System.out.println();
        System.out.printf("Three opt optimization started at %d", timeStart);
        StringBuilder sb ;
        TspTour bestTour = tspTour;
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < g.getSize() - 2; i++) {
                for (int j = i + 2; j < g.getSize() - 1; j++) {
                    for (int k = j + 2; k < g.getSize(); k++) {
                        List<Integer> newTour = getNewTour(bestTour.getTour(), i, j, k);
                        double newDistance = tourLength(newTour, g);
                        if (newDistance < bestTour.getLength()) {
                            bestTour = new TspTour(newTour, newDistance);
                            improved = true;
                        }
                    }
                }
            }
        }
        if(!testCase) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[");
            BufferedWriter bw = WriteDataToCSV.createBufferedWriter("three_opt_path.csv");
            Node end = null;
            for (int i = 0; i < bestTour.getTour().size() - 1; i++) {
                Node start = g.getNode(bestTour.getTour().get(i));
                end = g.getNode(bestTour.getTour().get(i + 1));
                sb = new StringBuilder("");
                sb.append(i + 1).append(",");
                sb2.append(start.getName()).append("-->");
                sb.append(start.getPos()).append("(").append(start.getName()).append("),");
                sb.append(start.getLat()).append(",").append(start.getLong()).append(",");
                sb.append(end.getPos()).append("(").append(end.getName()).append("),");
                sb.append(end.getLat()).append(",").append(end.getLong()).append(",");
                sb.append(g.getDistanceBetweenPoints(start.getPos(), end.getPos()));
                WriteDataToCSV.writeData(sb.toString(), bw);
            }
            sb2.append(end.getName()).append("]");
            WriteDataToCSV.closeStream(bw);
            System.out.println();
            System.out.printf("Length of tour after three opt : %f ", bestTour.getLength() * 1000);
            System.out.println();
            System.out.println("Details of the tour generated after three opt optimization can be found in the three_opt_path.csv file");
            long timeEnd = System.currentTimeMillis();
            System.out.printf("Three opt  optimization completed Successfully completed  at %d(ms)", timeEnd);
            System.out.println();
            System.out.println(sb2);
            System.out.printf("Time taken for Two Opt Optimization :  %d (ms)", timeEnd - timeStart);
            System.out.println();
        }

        return bestTour;
    }

    public static List<Integer> getNewTour(List<Integer> tour, int i, int j, int k) {
        List<Integer> newTour = new ArrayList<>();
        for (int x = 0; x <= i; x++) {
            newTour.add(tour.get(x));
        }
        for (int x = j; x > i; x--) {
            newTour.add(tour.get(x));
        }
        for (int x = k; x > j; x--) {
            newTour.add(tour.get(x));
        }
        for (int x = k + 1; x < tour.size(); x++) {
            newTour.add(tour.get(x));
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
