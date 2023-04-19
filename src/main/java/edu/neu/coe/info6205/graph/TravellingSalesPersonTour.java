package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.entity.TspTour;
import edu.neu.coe.info6205.util.WriteDataToCSV;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.util.*;

public class TravellingSalesPersonTour {

    public static TspTour findTravellingSalesPersonTour(Graph g, int start, GraphicsContext gc, Label l) {
        long timeStart = System.currentTimeMillis();
        System.out.println();
        System.out.printf("TSP generation using Christofides Started at %d", timeStart);
        List<List<Integer>> adjList = g.getAdjList();
        int n = adjList.size();
        StringBuilder sb;
        List<Integer> tour = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int[] degree = new int[n];
        for (int i = 0; i < adjList.size(); i++) {
            degree[i] = adjList.get(i).size();
        }
        TspTour tspTour = new TspTour();
        BufferedWriter bw = WriteDataToCSV.createBufferedWriter("tsp_path.csv");
        stack.push(start);
        double startPointX =-1;
        double startPointY =-1;
        double endPointX = -1;
        double endPointY = -1;
        double tspWeight = 0.0;

        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (degree[v] == 0) {
                if(tour.size() == 0){
                    startPointX = g.getXPoint(v);
                    startPointY = g.getYPoint(v);
                }else{
                    endPointX = g.getXPoint(v);
                    endPointY = g.getYPoint(v);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    double finalStartPointX = startPointX;
                    double finalStartPointY = startPointY;
                    double finalEndPointX = endPointX;
                    double finalEndPointY = endPointY;

                    Platform.runLater(() -> {
                        gc.setStroke(Color.BLUE);
                        gc.strokeLine(finalStartPointX, finalStartPointY, finalEndPointX, finalEndPointY);

                    });
                    startPointX = endPointX;
                    startPointY = endPointY;
                }
                tour.add(v);
                stack.pop();
            } else {
                int t = adjList.get(v).remove(--degree[v]);
                adjList.get(t).remove((Integer)(v));
                degree[t]--;
                stack.push(t);
            }
        }
        boolean[] visited = new  boolean[n];
        startPointX = g.getXPoint(start);
        startPointY = g.getYPoint(start);
        visited[0] = true;
        List<Integer> tspTourList = new ArrayList<>();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[").append(g.getNode(start).getName()).append( " ---> ");
        tspTourList.add(start);

        int u = start;
        int v;
        for(int i=1; i< tour.size(); i++){
            v = tour.get(i);
            if(!visited[v] || i == tour.size()-1){
                visited[tour.get(i)] = true;
                endPointX = g.getXPoint(v);
                endPointY = g.getYPoint(v);
                tspWeight+=g.getDistanceBetweenPoints(u,v);
                Node startNode = g.getNode(u);
                Node endnode = g.getNode(v);
                sb = new StringBuilder("");
                sb.append(i+1).append(",");
                sb.append(u).append("(").append(startNode.getName()).append("),");
                sb.append(startNode.getLat()).append(",").append(startNode.getLong()).append(",");
                sb.append(v).append("(").append(endnode.getName()).append("),");
                sb.append(endnode.getLat()).append(",").append(endnode.getLong()).append(",");
                sb.append(g.getDistanceBetweenPoints(u,v)*1000);
                WriteDataToCSV.writeData(sb.toString(), bw);
                tspTourList.add(v);
                sb2.append(g.getNode(v).getName()).append( "-->");
                double finalStartPointX = startPointX;
                double finalStartPointY = startPointY;
                double finalEndPointX = endPointX;
                double finalEndPointY = endPointY;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                double finalTspWeight = tspWeight;
                Platform.runLater(() -> {
                    gc.setStroke(Color.BLACK);
                    gc.strokeLine(finalStartPointX, finalStartPointY, finalEndPointX, finalEndPointY);
                    l.setText("Christofides  tour length (Black Line):" + finalTspWeight *1000);
                });
                startPointX = endPointX;
                startPointY = endPointY;
                u =v;
            }
        }
        WriteDataToCSV.closeStream(bw);
        System.out.println();
        System.out.printf("Length of tour after christofides algorithm: %f ",tspWeight*1000);
        System.out.println();
        System.out.println("Details of the tour generated after christofides algorithm can be found in the tsp_path.csv file");
        long timeEnd = System.currentTimeMillis();
        System.out.printf("TSP generation Successfully completed  at %d(ms)", timeEnd);
        System.out.println();

        System.out.printf("Time taken for TSP generation :  %d (ms)", timeEnd-timeStart);
        System.out.println();
        System.out.println(sb2);
        tspTour.setTour(tspTourList);
        tspTour.setLength(tspWeight);
        return tspTour;
    }
}
