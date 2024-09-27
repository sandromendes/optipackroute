package com.codejukebox.optipackroute.domain.models.dijkstra;

public class DijkstraResponse {

    private int[] distances;
    private int[] predecessors;

    public DijkstraResponse(int[] distances, int[] predecessors) {
        this.distances = distances;
        this.predecessors = predecessors;
    }

    public int[] getDistances() {
        return distances;
    }

    public int[] getPredecessors() {
        return predecessors;
    }
}
