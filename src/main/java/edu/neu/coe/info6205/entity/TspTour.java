package edu.neu.coe.info6205.entity;

import java.util.List;

public class TspTour {


    List<Integer> tour;

    double length;

    public TspTour(){

    }

    public TspTour(List<Integer> tour,double length ){
        this.tour = tour;
        this.length = length;
    }


    public List<Integer> getTour() {
        return tour;
    }

    public void setTour(List<Integer> tour) {
        this.tour = tour;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
