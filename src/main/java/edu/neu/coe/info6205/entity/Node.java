package edu.neu.coe.info6205.entity;

import javafx.scene.shape.Circle;

public class Node {

    private String name;

    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private double lat;
    private double lon;

    private double x;

    private double y;

    private int degree;

    public Circle getOval() {
        return oval;
    }

    public void setOval(Circle oval) {
        this.oval = oval;
    }

    Circle oval;




    public Node(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }


    public Node(String name, double lat, double lon, double x, double y){
        this.lat = lat;
        this.lon = lon;
        this.name= name;
        this.x = x;
        this.y = y;
        this.degree = 0;
    }

    public Node(String name, double lat, double lon, double x, double y,Circle oval){
        this.lat = lat;
        this.lon = lon;
        this.name= name;
        this.x = x;
        this.y = y;
        this.degree = 0;
        this.oval = oval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }
    public void setLon(long lon) {
        this.lon = lon;
    }

    public double getLong(){
        return this.lon;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getDegree() {
        return degree;
    }

    public int addDegree() {
        this.degree = this.degree+1;
        return this.degree;
    }

    public void setY(double y) {
        this.y = y;
    }
}
