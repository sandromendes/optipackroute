package com.codejukebox.optipackroute.core.algorithms.router.astar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.codejukebox.optipackroute.domain.models.astar.AStarResult;

public class AStarAlgorithm {

    private static final int INF = 999999;

    public AStarResult findShortestPath(int[][] matrix, int startNode, int goalNode) {
        int size = matrix.length;
        int[] gScores = new int[size];
        int[] fScores = new int[size];
        int[] predecessors = new int[size];
        boolean[] closedSet = new boolean[size];
        var openSet = new PriorityQueue<Node>(Comparator.comparingInt(node -> fScores[node.index]));

        Arrays.fill(gScores, INF);
        Arrays.fill(fScores, INF);
        Arrays.fill(predecessors, -1);
        
        gScores[startNode] = 0;
        fScores[startNode] = heuristic(startNode, goalNode);
        openSet.add(new Node(startNode, fScores[startNode]));

        while (!openSet.isEmpty()) {
            var currentNode = openSet.poll();
            int currentIndex = currentNode.index;

            if (currentIndex == goalNode) {
                return reconstructPath(predecessors, goalNode);
            }

            closedSet[currentIndex] = true;

            for (int i = 0; i < size; i++) {
                if (matrix[currentIndex][i] != INF && !closedSet[i]) {
                    int tentativeGScore = gScores[currentIndex] + matrix[currentIndex][i];
                    if (tentativeGScore < gScores[i]) {
                        predecessors[i] = currentIndex;
                        gScores[i] = tentativeGScore;
                        fScores[i] = gScores[i] + heuristic(i, goalNode);
                        openSet.add(new Node(i, fScores[i]));
                    }
                }
            }
        }
        return new AStarResult(new ArrayList<>(), INF);
    }

    private AStarResult reconstructPath(int[] predecessors, int goalNode) {
        var path = new ArrayList<Integer>();
        int currentNode = goalNode;
        while (currentNode != -1) {
            path.add(currentNode);
            currentNode = predecessors[currentNode];
        }
        Collections.reverse(path);
        return new AStarResult(path, path.size());
    }

    private int heuristic(int node, int goalNode) {
        // Example heuristic function (Manhattan distance in a 2D grid could be used here)
        return 0;
    }

    private static class Node {
        int index;
        int fScore;

        Node(int index, int fScore) {
            this.index = index;
            this.fScore = fScore;
        }
    }
}
