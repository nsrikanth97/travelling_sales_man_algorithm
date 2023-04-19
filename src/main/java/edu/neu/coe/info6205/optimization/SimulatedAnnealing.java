package edu.neu.coe.info6205.optimization;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.util.WriteDataToCSV;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.exp;
import static java.lang.Math.random;

public class SimulatedAnnealing {


    public static TspTour simulatedAnnealing(TspTour tspTour, double temp, double r, Graph g) {
        long timeStart = System.currentTimeMillis();
        System.out.println();
        System.out.printf("Simulated Annealing optimization started at %d", timeStart);
        List<Integer> tour = new ArrayList<>(tspTour.getTour());
        List<Integer> bestTour = new ArrayList<>(tspTour.getTour());
        double bestLength = tspTour.getLength();
        double currentLength = tspTour.getLength();
        int n = tour.size();
        boolean improved = true;
        double delta;
        Random random = new Random();
        int iteration = 0;
        int maxIteration = 1000;
        while (iteration < maxIteration) {
            iteration++;
            for (int i = 1; i < n - 3; i++) {
                for (int j = i + 1; j < n - 2; j++) {
                    if (i == j - 1 || i == j) {
                        continue;
                    }
                    List<Integer> newTour = twoOptSwap(tour, i, j);
                    double newLength = tourLength(newTour, g);
                    delta = newLength - currentLength;
                    double temperature = temp / (1 + r * iteration);
                    if (newLength < currentLength || acceptanceProbability(delta, temperature) > random.nextDouble()) {
                        currentLength = newLength;
                        tour = newTour;
                        if (newLength < bestLength) {
                            bestLength = newLength;
                            bestTour = newTour;
                        }
                    }
                }
            }
            temp *= r;
        }
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[");
        BufferedWriter bw = WriteDataToCSV.createBufferedWriter("simulated_annealing_path.csv");
        Node end = null;
        for (int i = 0; i < bestTour.size() - 1; i++) {
            Node start = g.getNode(bestTour.get(i));
            end = g.getNode(bestTour.get(i + 1));
            sb = new StringBuilder("");
            sb.append(i + 1).append(",");
            sb2.append(start.getName()).append("-->");
            sb.append(start.getPos()).append("(").append(start.getName()).append("),");
            sb.append(start.getLat()).append(",").append(start.getLong()).append(",");
            sb.append(end.getPos()).append("(").append(end.getName()).append("),");
            sb.append(end.getLat()).append(",").append(end.getLong()).append(",");
            sb.append(g.getDistanceBetweenPoints(start.getPos(), end.getPos()) * 1000);
            WriteDataToCSV.writeData(sb.toString(), bw);
        }
        sb2.append(end.getName()).append("]");
        WriteDataToCSV.closeStream(bw);
        System.out.println();
        System.out.printf("Length of tour after Simulated Annealing : %f ", bestLength * 1000);
        System.out.println();
        System.out.println("Details of the tour generated after Simulated annealing optimization can be found in the simulated_annealing_path.csv file");
        long timeEnd = System.currentTimeMillis();
        System.out.printf("Simulated Annealing optimization Successfully completed  at %d(ms)", timeEnd);
        System.out.println();
        System.out.println(sb2);
        System.out.printf("Time taken for Simulated Annealing Optimization :  %d (ms)", timeEnd - timeStart);
        return new TspTour(bestTour, bestLength);
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

    public static double acceptanceProbability(double delta, double T){
        return exp(-delta/T);
    }


}
