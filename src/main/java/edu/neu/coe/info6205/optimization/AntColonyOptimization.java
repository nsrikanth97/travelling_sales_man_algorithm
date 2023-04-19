package edu.neu.coe.info6205.optimization;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.graph.Graph;
import edu.neu.coe.info6205.util.WriteDataToCSV;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AntColonyOptimization {

    // Number of ants
    private int numAnts;

    // Number of iterations
    private int numIterations;

    // Number of cities
    private int numCities;

    // Distance matrix
    private double[][] distanceMatrix;

    // Pheromone matrix
    private double[][] pheromoneMatrix;

    private double[][] delta;

    // Constructor
    public AntColonyOptimization(int numAnts, int numIterations, Graph g,List<Integer> tour) {
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.numCities = g.getSize();
        this.distanceMatrix = g.getAdjMatrix();
        this.pheromoneMatrix = new double[numCities][numCities];
        this.delta = new double[numCities][numCities];

        for(int i=0; i<numCities-1;i++){
            pheromoneMatrix[tour.get(i)][tour.get(i+1)]  = 2;
            pheromoneMatrix[tour.get(i+1)][tour.get(i)]  = 2;
        }
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if(pheromoneMatrix[i][j] != 2){
                    pheromoneMatrix[i][j] = 0.01;
                    pheromoneMatrix[j][i] = 0.01;
                }
            }
        }
    }

    // Run the ant colony optimization
    public void run(Graph g) {
        long timeStart = System.currentTimeMillis();
        System.out.println();

        System.out.printf("ACO  optimization started at %d", timeStart);
        Ant bestAnt = null;

        while (true){
            Ant[] ants = new Ant[numAnts];
            for (int j = 0; j < numAnts; j++) {
                ants[j] = new Ant(numCities, new Random().nextInt(numCities));
            }
            for (int j = 0; j < numAnts; j++) {
                for (int k = 0; k < numCities - 1; k++) {
                    ants[j].selectNextCity(pheromoneMatrix,distanceMatrix);
                }
                ants[j].getTour().add(ants[j].getTour().get(0));
            }
            for (int j = 0; j < numAnts; j++) {
                ants[j].calculateCost(distanceMatrix);
            }
            for (int j = 0; j < numAnts; j++) {
                if(bestAnt == null){
                    bestAnt = ants[j];
                }else if (ants[j].getCost() < bestAnt.getCost()) {
                    bestAnt = ants[j];
                }
            }
            for (int  j= 0; j < numCities; j++) {
                for (int k = 0; k < numCities; k++) {
                    delta[j][k] = 0;
                }
            }
            for (Ant ant : ants) {
                double contribution = 1 / ant.getCost();
                for (int c = 0; c < numCities - 1; c++) {
                    delta[ant.getTour().get(c)][ant.getTour().get(c + 1)] += contribution;
                    delta[ant.getTour().get(c+1)][ant.getTour().get(c)] =delta[ant.getTour().get(c)][ant.getTour().get(c + 1)];
                }
            }

            for (int j = 0; j < numCities; j++) {
                for (int k = 0; k < numCities; k++) {
                    pheromoneMatrix[j][k] = 0.5*pheromoneMatrix[j][k];
                    pheromoneMatrix[j][k] += delta[j][k];
                }
            }
            if (isConverged()) {
                break;
            }
        }

        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[");
        BufferedWriter bw = WriteDataToCSV.createBufferedWriter("aco_path.csv");
        Node end = null;
        for (int k = 0; k < bestAnt.getTour().size() - 1; k++) {
            Node start = g.getNode(bestAnt.getTour().get(k));
            end = g.getNode(bestAnt.getTour().get(k + 1));
            sb = new StringBuilder("");
            sb.append(k + 1).append(",");
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
        System.out.printf("Length of tour after ACO  : %f ", bestAnt.getCost() * 1000);
        System.out.println();
        System.out.println("Details of the tour generated after ACO optimization can be found in the aco_optimization_path.csv file");
        long timeEnd = System.currentTimeMillis();
        System.out.printf("ACO Successfully completed  at %d(ms)", timeEnd);
        System.out.println(sb2);
        System.out.println();
        System.out.printf("Time taken for ACO :  %d (ms)", timeEnd - timeStart);

    }

    private boolean isConverged() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (delta[i][j] > 0.0001) {
                    return false;
                }
            }
        }
        return true;
    }

    // Ant class
    private class Ant {
        // Tour
        private ArrayList<Integer> tour;


        // Cost
        private double cost;

        // Constructor
        public Ant(int numCities, int start) {
            tour = new ArrayList<Integer>();
            tour.add(start);
        }

        // Select the next city
        public void selectNextCity(double[][] pheromoneMatrix,double[][] distance) {
            int currentCity = tour.get(tour.size() - 1);

// Calculate the probabilities
            double[] probabilities = new double[numCities];
            double sum = 0.0;
            for (int i = 0; i < numCities; i++) {
                if (!tour.contains(i) && i != currentCity &&  distanceMatrix[currentCity][i] >0) {
                    probabilities[i] = Math.pow(pheromoneMatrix[currentCity][i], 10) / distanceMatrix[currentCity][i];
                    sum += probabilities[i];
                }
            }

// Normalize the probabilities
            for (int i = 0; i < numCities; i++) {
                probabilities[i] /= sum;
            }

// Select the next city
            Random random = new Random();
            double p = random.nextDouble();

//            double cumulativeProbability = 1.0;
//            for (int i = 0; i < numCities; i++) {
//                if(i == currentCity)
//                    continue;
//                cumulativeProbability -= probabilities[i-1];
//                if (p > cumulativeProbability) {
//                    tour.add(i-1);
//                    break;
//                }else if(i== numCities-1){
//                    tour.add(i);
//                }
//            }
            sum = 0.0;
            for (int i = 0; i < numCities; i++) {
                sum += probabilities[i];
                if (p <= sum) {
                    tour.add(i);
                    break;
                }
            }

        }

        // Calculate the cost of the tour
        public void calculateCost(double[][] distanceMatrix) {
            cost = 0;
            for (int i = 0; i < tour.size() - 1; i++) {
                cost += distanceMatrix[tour.get(i)][tour.get(i + 1)];
            }
        }

        // Getters
        public ArrayList<Integer> getTour() {
            return tour;
        }

        public double getCost() {
            return cost;
        }
    }

}