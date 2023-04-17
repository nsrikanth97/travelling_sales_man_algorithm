package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;

import java.util.ArrayList;
import java.util.List;

public class GraphUsingMatrix implements Graph {

    private int V;
    private List<Node> nodeList;

    private List<Node> degreeGreater;
    private double[][] adjMatrix;

    public double[][] getPheromoneMatrix() {
        return pheromoneMatrix;
    }

    public void setPheromoneMatrix(double[][] pheromoneMatrix) {
        this.pheromoneMatrix = pheromoneMatrix;
    }

    private double[][] pheromoneMatrix;


    private List<List<Integer>> adjList;

    public GraphUsingMatrix(int V){
        this.V = V;
        nodeList = new ArrayList<>();
        degreeGreater = new ArrayList<>();
        adjMatrix = new double[V][V];
        pheromoneMatrix = new double[V][V];
        adjList = new ArrayList<>();
        for(int i =0; i < V ; i++){
            adjList.add(new ArrayList<>());
        }
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public void addEdge(int u, int v) {
        if(u == v )
            return;
        double distance = getDistance(u, v);
        adjMatrix[u][v] = distance;
        adjMatrix[v][u] = distance;
    }

    public Node getNode(int i) {
        return nodeList.get(i);
    }

    public int getSize() {
        return this.V;
    }

    public double getDistance(int i, int j) {
        Node node1 = nodeList.get(i);
        Node node2 = nodeList.get(j);
        double lat1 = node1.getLat();
        double lon1 = node1.getLong();
        double lat2 = node2.getLat();
        double lon2 = node2.getLong();
        double earthRadius = 6371; // in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance;
    }

    public double getXPoint(int i){
        return nodeList.get(i).getX();
    }

    public double getYPoint(int i){
        return nodeList.get(i).getY();
    }

    public double getDistanceBetweenPoints(int u, int v){
        return this.adjMatrix[u][v];
    }

    public void addNodeToDegreeList(Node node){
        this.degreeGreater.add(node);
    }

    public void removeNodeToDegreeList(Node node){
        this.degreeGreater.remove(node);
    }
    public int sizeOfDegreeNodes(){
        return this.degreeGreater.size();
    }

    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public void setAdjMatrix(double[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public List<Node> getDegreeGreaterList(){
        return this.degreeGreater;
    }

    public List<List<Integer>> getAdjList() {
        return adjList;
    }

    public void setAdjList(List<List<Integer>> adjList) {
        this.adjList = adjList;
    }


}