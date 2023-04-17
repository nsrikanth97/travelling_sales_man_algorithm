package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.TspTour;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.*;

public class TravellingSalesPersonTour {

    public static TspTour findTravellingSalesPersonTour(Graph g, int start, GraphicsContext gc, Label l) {
        List<List<Integer>> adjList = g.getAdjList();
        int n = adjList.size();
        List<Integer> tour = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int[] degree = new int[n];
        for (int i = 0; i < adjList.size(); i++) {
            degree[i] = adjList.get(i).size();
        }
        TspTour tspTour = new TspTour();

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
                tspTourList.add(v);
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
        tspTour.setTour(tspTourList);
        tspTour.setLength(tspWeight);
        return tspTour;
    }
}
