package com.codejukebox.optipackroute.dtos.astar;

import java.util.List;

public class AStarResponse {

    private List<Integer> path;
    private int totalCost;

    public AStarResponse(List<Integer> path, int totalCost) {
        this.path = path;
        this.totalCost = totalCost;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getTotalCost() {
        return totalCost;
    }
}