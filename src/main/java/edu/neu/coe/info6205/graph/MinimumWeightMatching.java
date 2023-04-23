package edu.neu.coe.info6205.graph;

import edu.neu.coe.info6205.entity.Edge;
import edu.neu.coe.info6205.entity.Node;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MinimumWeightMatching {

    public static List<Edge> findMinimumWeightMatching(Graph g,Pane root) {
        List<Node> points = g.getOddDegreeList();
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));
        boolean[] matched = new boolean[g.getSize()];
        int sPoint;
        int ePoint;
        double weight;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                sPoint = points.get(i).getPos();
                ePoint = points.get(j).getPos();
                weight = g.getDistanceBetweenPoints(sPoint,ePoint);
                edgeQueue.offer(new Edge(points.get(i).getPos(), points.get(j).getPos(),weight));
            }
        }
        List<List<Integer>> adjList = g.getAdjList();
        List<Edge> matching = new ArrayList<>();
        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int index1 = edge.getU();
            int index2 = edge.getV();

            if (!matched[index1] && !matched[index2]) {
                matched[index1] = true;
                matched[index2] = true;
                matching.add(edge);
                adjList.get(edge.getU()).add(edge.getV());
                adjList.get(edge.getV()).add(edge.getU());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Node start = g.getNode(edge.getU());
                Node end = g.getNode(edge.getV());
                double startXPoint = start.getX();
                double startYPoint = start.getY();
                double endXPoint = end.getX();
                double endYPoint = end.getY();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if(root!=null)
                Platform.runLater(() -> {
                    Line line = new Line(startXPoint, startYPoint, endXPoint, endYPoint);
                    line.setStroke(Color.RED);
                    line.setStrokeWidth(1.5);
                    Tooltip tooltip = new Tooltip(start.getName() + " " + g.getDistanceBetweenPoints(start.getPos(), end.getPos()) + " " +  end.getName());
                    Tooltip.install(line, tooltip);
                    root.getChildren().add(line);
                });

            }
            if(matching.size() == points.size()/2)
                break;
        }
        g.setAdjList(adjList);
        return matching;
    }

}
