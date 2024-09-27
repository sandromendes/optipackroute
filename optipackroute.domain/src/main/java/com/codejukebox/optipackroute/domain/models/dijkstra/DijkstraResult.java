package com.codejukebox.optipackroute.domain.models.dijkstra;

public class DijkstraResult {

    private int[] distances;
    private int[] predecessors;

    public DijkstraResult(int[] distances, int[] predecessors) {
        this.distances = distances;
        this.predecessors = predecessors;
    }

    public int[] getDistances() {
        return distances;
    }

    public int[] getPredecessors() {
        return predecessors;
    }

    // Utility methods for printing results
    public void printResult() {
        System.out.println("Distances from start node:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("Node " + i + ": " + distances[i]);
        }

        System.out.println("\nPredecessors:");
        for (int i = 0; i < predecessors.length; i++) {
            System.out.println("Node " + i + ": " + predecessors[i]);
        }
    }
}
