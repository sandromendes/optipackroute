package com.codejukebox.optipackroute.domain.models.floydwarshall;

import java.util.List;

public class FloydWarshallRequest {
    private int[][] matrix;
    private List<Integer> nodes;
    private int initialNode;

    // Getters e Setters
    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(List<Integer> nodes) {
        this.nodes = nodes;
    }

    public int getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(int initialNode) {
        this.initialNode = initialNode;
    }
}
