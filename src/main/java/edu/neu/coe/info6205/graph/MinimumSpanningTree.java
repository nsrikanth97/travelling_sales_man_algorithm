package edu.neu.coe.info6205.graph;


import edu.neu.coe.info6205.entity.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinimumSpanningTree {

    public static double generateMST(Graph g, int S){

        Queue<Edge> queue = new PriorityQueue<>((o1, o2) -> Double.compare(o1.getWeight(), o2.getWeight()));
        double mstWeight = 0;
        List<Edge> mst = new ArrayList<>();
        boolean[] visited = new boolean[g.getSize()];

        visited[S] = true;

        for(int i= 0 ; i < g.getSize(); i++){
            if(!visited[i])
                queue.offer(new Edge(S, i, g.getDistanceBetweenPoints(S,i)));
        }
        List<List<Integer>> adjList = g.getAdjList();
        Edge edge;
        while(!queue.isEmpty()){
            edge = queue.poll();
            if(visited[edge.getV()])
                continue;
            mst.add(edge);
            adjList.get(edge.getU()).add(edge.getV());
            adjList.get(edge.getV()).add(edge.getU());


            visited[edge.getV()] = true;
            for(int i= 0 ; i < g.getSize(); i++){
                if(!visited[i])
                    queue.offer(new Edge(edge.getV(), i, g.getDistanceBetweenPoints(edge.getV(),i)));
            }
            if(mst.size() >= g.getSize()-1){
                break;
            }
        }
        g.setAdjList(adjList);
        return mstWeight;
    }
}
