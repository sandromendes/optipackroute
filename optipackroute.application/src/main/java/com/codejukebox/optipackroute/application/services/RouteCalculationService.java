package com.codejukebox.optipackroute.application.services;

import java.util.ArrayList;

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

    public DijkstraResponse calculateOptimalPath(DijkstraRequest request) {
        int[][] matrix = request.getMatrix();
        int startNode = request.getStartNode();

        var dijkstraAlgorithm = new DijkstraAlgorithm();
        var result = dijkstraAlgorithm.findShortestPaths(matrix, startNode);

        return new DijkstraResponse(result.getDistances(), result.getPredecessors());
    }
	
    public AStarResponse calculateShortestPath(AStarRequest request) {
        var algorithm = new AStarAlgorithm();
        var result = algorithm.findShortestPath(request.getMatrix(), request.getStartNode(), request.getGoalNode());

        return new AStarResponse(result.getPath(), result.getTotalCost());
    }
    
    public FloydWarshallResponse calculateOptimalPath(FloydWarshallRequest request) {
        int[][] matrix = request.getMatrix();
        var nodes = request.getNodes();
        int initialNode = request.getInitialNode();

        var floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
        floydWarshall.runPreProcessor();

        // Converte a lista de inteiros para um ArrayList
        var nodesList = new ArrayList<>(nodes);
        floydWarshall.findOptimalConfiguration(nodesList, initialNode);

        // Obtém o resultado
        var result = floydWarshall.getResult();

        // Cria a resposta com o resultado do algoritmo
        return new FloydWarshallResponse(
                result.getPath(),
                result.getTotalCost(),
                result.getDistanceMatrix(),
                result.getPredecessorMatrix(),
                result.getSubPaths()
        );
    }
}
