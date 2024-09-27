package com.codejukebox.optipackroute.domain.models.astar;

public class AStarRequest {
    private int[][] matrix;
    private int startNode;
    private int goalNode;

    public AStarRequest(int[][] matrix, int startNode, int goalNode) {
        this.matrix = matrix;
        this.startNode = startNode;
        this.goalNode = goalNode;
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

    public int getGoalNode() {
        return goalNode;
    }

    public void setGoalNode(int goalNode) {
        this.goalNode = goalNode;
    }
}