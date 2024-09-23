package com.codejukebox.optipackroute.dtos.dijkstra;

public class DijkstraRequest {
    private int[][] matrix;
    private int startNode;

    public DijkstraRequest(int[][] matrix, int startNode) {
        this.matrix = matrix;
        this.startNode = startNode;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getStartNode() {
        return startNode;
    }

    public void setStartNode(int startNode) {
        this.startNode = startNode;
    }
}
