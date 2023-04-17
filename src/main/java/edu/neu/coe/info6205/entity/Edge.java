package edu.neu.coe.info6205.entity;

public class Edge {
    int u, v;
    double weight;

    public Edge(int u, int v, double weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "(" + u + ", " + v + ", " + weight + ")";
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
