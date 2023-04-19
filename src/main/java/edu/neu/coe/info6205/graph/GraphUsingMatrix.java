package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;

import java.util.ArrayList;
import java.util.List;

public class GraphUsingMatrix implements Graph {

    private int V;
    private List<Node> nodeList;

    public void setOddDegreeNodes(List<Node> oddDegreeNodes) {
        this.oddDegreeNodes = oddDegreeNodes;
    }

    private List<Node> oddDegreeNodes;
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
        oddDegreeNodes = new ArrayList<>();
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

    //added for junit
    public void addEdge(int u, int v, double weight) {
        adjMatrix[u][v]  = weight;
        adjMatrix[v][u] = weight;
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

    public void addNodeToOddDegreeList(Node node){
        this.oddDegreeNodes.add(node);
    }

    public void removeNodeFromOddDegreeList(Node node){
        this.oddDegreeNodes.remove(node);
    }
    public int sizeOfOddDegreeNodes(){
        return this.oddDegreeNodes.size();
    }

    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public void setAdjMatrix(double[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public List<Node> getOddDegreeList(){
        return this.oddDegreeNodes;
    }

    public List<List<Integer>> getAdjList() {
        return adjList;
    }

    public void setAdjList(List<List<Integer>> adjList) {
        this.adjList = adjList;
    }

    public void addAllEdges(){
        for(int i= 0; i < this.getSize() ; i++){
            for(int j=0; j< this.getSize() ; j++){
                this.addEdge(i,j);
            }
        }
    }

    //added for junit
    public void setDistanceBetweenPoints(int p1, int p2, double distance) {
        adjMatrix[p1][p2] = distance;
        adjMatrix[p2][p1] = distance; // assume undirected graph
    }
    public List<Node> getNodeList(){
        return nodeList;
    }


}
