package com.codejukebox.optipackroute.application.services;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.core.algorithms.router.astar.AStarAlgorithm;
import com.codejukebox.optipackroute.core.algorithms.router.dijkstra.DijkstraAlgorithm;
import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.domain.models.astar.AStarRequest;
import com.codejukebox.optipackroute.domain.models.astar.AStarResponse;
import com.codejukebox.optipackroute.domain.models.dijkstra.DijkstraRequest;
import com.codejukebox.optipackroute.domain.models.dijkstra.DijkstraResponse;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallRequest;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallResponse;

@Service
public class RouteCalculationService {

    private static final Logger logger = LoggerFactory.getLogger(RouteCalculationService.class);

    public DijkstraResponse calculateOptimalPath(DijkstraRequest request) {
        logger.info("Starting Dijkstra algorithm with request: {}", request);
        try {
            int[][] matrix = request.getMatrix();
            int startNode = request.getStartNode();

            var dijkstraAlgorithm = new DijkstraAlgorithm();
            var result = dijkstraAlgorithm.findShortestPaths(matrix, startNode);

            logger.info("Dijkstra algorithm completed successfully.");
            return new DijkstraResponse(result.getDistances(), result.getPredecessors());
        } catch (Exception e) {
            logger.error("Error during Dijkstra algorithm: {}", e.getMessage(), e);
            throw new RuntimeException("Path calculation failed: " + e.getMessage(), e);
        }
    }

    public AStarResponse calculateShortestPath(AStarRequest request) {
        logger.info("Starting A* algorithm with request: {}", request);
        try {
            var algorithm = new AStarAlgorithm();
            var result = algorithm.findShortestPath(request.getMatrix(), request.getStartNode(), request.getGoalNode());

            logger.info("A* algorithm completed successfully.");
            return new AStarResponse(result.getPath(), result.getTotalCost());
        } catch (Exception e) {
            logger.error("Error during A* algorithm: {}", e.getMessage(), e);
            throw new RuntimeException("Path calculation failed: " + e.getMessage(), e);
        }
    }

    public FloydWarshallResponse calculateOptimalPath(FloydWarshallRequest request) {
        logger.info("Starting Floyd-Warshall algorithm with request: {}", request);
        try {
            var matrix = request.getMatrix();
            var nodes = request.getNodes();
            int initialNode = request.getInitialNode();

            var floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
            floydWarshall.runPreProcessor();

            var nodesList = new ArrayList<>(nodes);
            floydWarshall.findOptimalConfiguration(nodesList, initialNode);

            var result = floydWarshall.getResult();

            logger.info("Floyd-Warshall algorithm completed successfully.");
            return new FloydWarshallResponse(
                    result.getPath(),
                    result.getTotalCost(),
                    result.getDistanceMatrix(),
                    result.getPredecessorMatrix(),
                    result.getSubPaths()
            );
        } catch (Exception e) {
            logger.error("Error during Floyd-Warshall algorithm: {}", e.getMessage(), e);
            throw new RuntimeException("Path calculation failed: " + e.getMessage(), e);
        }
    }
}
