package com.codejukebox.optipackroute.domain.models.floydwarshall;

import java.util.HashMap;
import java.util.List;

public class FloydWarshallResponse {
    private List<Integer> optimalPath;
    private int totalCost;
    private int[][] distanceMatrix;
    private int[][] predecessorMatrix;
    private HashMap<Integer, List<PathCost>> subPaths;

    public FloydWarshallResponse(List<Integer> optimalPath, int totalCost, int[][] distanceMatrix, int[][] predecessorMatrix, HashMap<Integer, List<PathCost>> subPaths) {
        this.optimalPath = optimalPath;
        this.totalCost = totalCost;
        this.distanceMatrix = distanceMatrix;
        this.predecessorMatrix = predecessorMatrix;
        this.subPaths = subPaths;
    }

    // Getters e Setters
    public List<Integer> getOptimalPath() {
        return optimalPath;
    }

    public void setOptimalPath(List<Integer> optimalPath) {
        this.optimalPath = optimalPath;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public int[][] getPredecessorMatrix() {
        return predecessorMatrix;
    }

    public void setPredecessorMatrix(int[][] predecessorMatrix) {
        this.predecessorMatrix = predecessorMatrix;
    }

    public HashMap<Integer, List<PathCost>> getSubPaths() {
        return subPaths;
    }

    public void setSubPaths(HashMap<Integer, List<PathCost>> subPaths) {
        this.subPaths = subPaths;
    }
}