package com.codejukebox.optipackroute.domain.models.astar;

import java.util.List;

public class AStarResult {

    private List<Integer> path;
    private int totalCost;

    public AStarResult(List<Integer> path, int totalCost) {
        this.path = path;
        this.totalCost = totalCost;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getTotalCost() {
        return totalCost;
    }

    // Utility methods for printing results
    public void printResult() {
        System.out.println("Path found:");
        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println(String.join(" -> ", path.stream().map(String::valueOf).toArray(String[]::new)));
        }

        System.out.println("\nTotal Cost: " + totalCost);
    }
}
