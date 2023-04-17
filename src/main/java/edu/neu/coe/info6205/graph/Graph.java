package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;

import java.util.List;

public interface  Graph{
    void addNode(Node node);

    void addEdge(int u, int v);


    double getDistanceBetweenPoints(int u, int v);

    Node getNode(int i);

    int getSize();

    double getXPoint(int i);

    double getYPoint(int i);

    void addNodeToOddDegreeList(Node node);

    void removeNodeFromOddDegreeList(Node node);

    int sizeOfOddDegreeNodes();

    double[][] getAdjMatrix();

    List<Node> getOddDegreeList();

    List<List<Integer>> getAdjList();

    void setAdjList(List<List<Integer>> adjList);

    double[][] getPheromoneMatrix();

    void setPheromoneMatrix(double[][] pheromoneMatrix);

    void addAllEdges();

    List<Node> getNodeList();
}
