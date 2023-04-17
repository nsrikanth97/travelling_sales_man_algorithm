package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Node;

import java.util.ArrayList;
import java.util.List;

public interface  Graph{
    void addNode(Node node);

    void addEdge(int u, int v);


    double getDistanceBetweenPoints(int u, int v);

    Node getNode(int i);

    int getSize();

    double getXPoint(int i);

    double getYPoint(int i);

    void addNodeToDegreeList(Node node);

    void removeNodeToDegreeList(Node node);

    int sizeOfDegreeNodes();

    double[][] getAdjMatrix();

    List<Node> getDegreeGreaterList();

    List<List<Integer>> getAdjList();

    void setAdjList(List<List<Integer>> adjList);

    double[][] getPheromoneMatrix();

    void setPheromoneMatrix(double[][] pheromoneMatrix);

}
