package com.codejukebox.optipackroute.services;

import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.dtos.dijkstra.DijkstraRequest;
import com.codejukebox.optipackroute.dtos.dijkstra.DijkstraResponse;
import com.codejukebox.optipackroute.heuristics.dijkstra.DijkstraAlgorithm;
import com.codejukebox.optipackroute.heuristics.dijkstra.domain.DijkstraResult;

@Service
public class DijkstraService {

    public DijkstraResponse calculateOptimalPath(DijkstraRequest request) {
        int[][] matrix = request.getMatrix();
        int startNode = request.getStartNode();

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        DijkstraResult result = dijkstraAlgorithm.findShortestPaths(matrix, startNode);

        return new DijkstraResponse(result.getDistances(), result.getPredecessors());
    }
}
