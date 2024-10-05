package com.codejukebox.optipackroute.domain.models.floydwarshall;

import java.util.HashMap;
import java.util.List;

public class FloydWarshallResponse {
    private List<Integer> optimalPath;
    private double totalCost;
    private double[][] distanceMatrix;
    private double[][] predecessorMatrix;
    private HashMap<Integer, List<PathCost>> subPaths;

    public FloydWarshallResponse(List<Integer> optimalPath, 
    		double totalCost, 
    		double[][] distanceMatrix, 
    		double[][] predecessorMatrix, 
    		HashMap<Integer, List<PathCost>> subPaths) {
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public double[][] getPredecessorMatrix() {
        return predecessorMatrix;
    }

    public void setPredecessorMatrix(double[][] predecessorMatrix) {
        this.predecessorMatrix = predecessorMatrix;
    }

    public HashMap<Integer, List<PathCost>> getSubPaths() {
        return subPaths;
    }

    public void setSubPaths(HashMap<Integer, List<PathCost>> subPaths) {
        this.subPaths = subPaths;
    }
}