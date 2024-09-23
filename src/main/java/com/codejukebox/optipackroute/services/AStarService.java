package com.codejukebox.optipackroute.services;

import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.dtos.astar.AStarRequest;
import com.codejukebox.optipackroute.dtos.astar.AStarResponse;
import com.codejukebox.optipackroute.heuristics.astar.AStarAlgorithm;
import com.codejukebox.optipackroute.heuristics.astar.domain.AStarResult;

@Service
public class AStarService {

    public AStarResponse calculateShortestPath(AStarRequest request) {
        AStarAlgorithm algorithm = new AStarAlgorithm();
        AStarResult result = algorithm.findShortestPath(request.getMatrix(), request.getStartNode(), request.getGoalNode());

        return new AStarResponse(result.getPath(), result.getTotalCost());
    }
}
