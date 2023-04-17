package edu.neu.coe.info6205.graph;


import edu.neu.coe.info6205.entity.Edge;
import edu.neu.coe.info6205.entity.Node;
import edu.neu.coe.info6205.util.WriteDataToCSV;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinimumSpanningTree {

    public static double generateMST(Graph g, int S, GraphicsContext gc, Label label){

        Queue<Edge> queue = new PriorityQueue<>((o1, o2) -> Double.compare(o1.getWeight(), o2.getWeight()));
        double mstWeight = 0;
        List<Edge> mst = new ArrayList<>();
        boolean[] visited = new boolean[g.getSize()];

        visited[S] = true;
        StringBuilder sb = new StringBuilder("");
        for(int i= 0 ; i < g.getSize(); i++){
            if(!visited[i])
                queue.offer(new Edge(S, i, g.getDistanceBetweenPoints(S,i)));
        }
        List<List<Integer>> adjList = g.getAdjList();
        Edge edge;
        BufferedWriter bw = WriteDataToCSV.createBufferedWriter("mst_path.csv");
        while(!queue.isEmpty()){
            edge = queue.poll();
            if(visited[edge.getV()])
                continue;
            mst.add(edge);
            adjList.get(edge.getU()).add(edge.getV());
            adjList.get(edge.getV()).add(edge.getU());
            Node start = g.getNode(edge.getU());
            int degreeS = start.addDegree();
            Node end = g.getNode(edge.getV());
            int degreeE = end.addDegree();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            mstWeight += edge.getWeight();
            double finalMstWeight = mstWeight;
            double startXPoint = start.getX();
            double startYPoint = start.getY();
            double endXPoint = end.getX();
            double endYPoint = end.getY();
            Edge finalEdge = edge;

            sb = new StringBuilder("");
            sb.append(mst.size()).append(",");
            sb.append(edge.getU()).append("(").append(start.getName()).append("),");
            sb.append(start.getLat()).append(",").append(start.getLong()).append(",");
            sb.append(edge.getV()).append("(").append(end.getName()).append("),");
            sb.append(end.getLat()).append(",").append(end.getLong()).append(",");
            sb.append(edge.getWeight()*1000);
            WriteDataToCSV.writeData(sb.toString(),bw);
//            System.out.println(edge.getU() +" " +edge.getV() + " " + edge.getWeight());

            Platform.runLater(() -> {

                gc.strokeLine(startXPoint,startYPoint,endXPoint,endYPoint);
                label.setText("Length of MST tour : " + finalMstWeight);
                if(degreeS%2 != 0){
                    gc.setFill(Color.RED);
                    gc.fillOval(startXPoint, startYPoint, 5, 5);
                    start.setPos(finalEdge.getU());
                    g.addNodeToOddDegreeList(start);
                }else{
                    gc.setFill(Color.BLACK);
                    gc.fillOval(startXPoint, startYPoint, 5, 5);
                    start.setPos(finalEdge.getU());
                    g.removeNodeFromOddDegreeList(start);
                }
                if(degreeE%2 != 0){
                    gc.setFill(Color.RED);
                    gc.fillOval(endXPoint, endYPoint, 5, 5);
                    end.setPos(finalEdge.getV());
                    g.addNodeToOddDegreeList(end);
                }else{
                    gc.setFill(Color.BLACK);
                    gc.fillOval(startXPoint, startYPoint, 5, 5);
                    end.setPos(finalEdge.getV());
                    g.removeNodeFromOddDegreeList(end);
                }
            });
            visited[edge.getV()] = true;
            for(int i= 0 ; i < g.getSize(); i++){
                if(!visited[i])
                    queue.offer(new Edge(edge.getV(), i, g.getDistanceBetweenPoints(edge.getV(),i)));
            }
            if(mst.size() >= g.getSize()-1){
                break;
            }
        }
        WriteDataToCSV.closeStream(bw);
        g.setAdjList(adjList);
        return mstWeight;
    }
}
