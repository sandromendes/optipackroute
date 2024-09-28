package com.codejukebox.optipackroute.core.algorithms.router.dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

import com.codejukebox.optipackroute.domain.models.dijkstra.DijkstraResult;

public class DijkstraAlgorithm {

    private static final int INF = 999999;

    public DijkstraResult findShortestPaths(int[][] matrix, int startNode) {
        int size = matrix.length;
        int[] distances = new int[size];
        int[] predecessors = new int[size];
        boolean[] visited = new boolean[size];
        
        Arrays.fill(distances, INF);
        Arrays.fill(predecessors, -1);
        distances[startNode] = 0;

        var priorityQueue = new PriorityQueue<Node>((a, b) -> Integer.compare(a.distance, b.distance));
        priorityQueue.add(new Node(startNode, 0));

        while (!priorityQueue.isEmpty()) {
            var currentNode = priorityQueue.poll();
            int currentIndex = currentNode.index;

            if (visited[currentIndex]) continue;
            visited[currentIndex] = true;

            for (int i = 0; i < size; i++) {
                if (matrix[currentIndex][i] != INF) {
                    int newDist = distances[currentIndex] + matrix[currentIndex][i];
                    if (newDist < distances[i]) {
                        distances[i] = newDist;
                        predecessors[i] = currentIndex;
                        priorityQueue.add(new Node(i, newDist));
                    }
                }
            }
        }

        return new DijkstraResult(distances, predecessors);
    }

    private static class Node {
        int index;
        int distance;

        Node(int index, int distance) {
            this.index = index;
            this.distance = distance;
        }
    }
}
