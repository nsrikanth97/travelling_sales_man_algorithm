package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.TspTour;

import java.util.*;

public class TravellingSalesPersonTour {

    public static TspTour findTravellingSalesPersonTour(Graph g, int start) {
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
        double tspWeight = 0.0;
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (degree[v] == 0) {
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
        visited[0] = true;
        List<Integer> tspTourList = new ArrayList<>();
        tspTourList.add(start);
        int u = start;
        int v;
        for(int i=1; i< tour.size(); i++){
            v = tour.get(i);
            if(!visited[v] || i == tour.size()-1){
                visited[tour.get(i)] = true;
                tspWeight+=g.getDistanceBetweenPoints(u,v);
                tspTourList.add(v);
                u =v;
            }
        }
        tspTour.setTour(tspTourList);
        tspTour.setLength(tspWeight);
        return tspTour;
    }
}
