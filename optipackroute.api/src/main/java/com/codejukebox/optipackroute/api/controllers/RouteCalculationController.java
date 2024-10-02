package com.codejukebox.optipackroute.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codejukebox.optipackroute.application.services.RouteCalculationService;
import com.codejukebox.optipackroute.domain.models.astar.AStarRequest;
import com.codejukebox.optipackroute.domain.models.astar.AStarResponse;
import com.codejukebox.optipackroute.domain.models.dijkstra.DijkstraRequest;
import com.codejukebox.optipackroute.domain.models.dijkstra.DijkstraResponse;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallRequest;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallResponse;

@RestController
@RequestMapping("/api/v1/shortest-path/")
public class RouteCalculationController {

    private static final Logger logger = LoggerFactory.getLogger(RouteCalculationController.class);

    @Autowired
    private RouteCalculationService aStarService;

    @PostMapping("/floyd-warshall")
    public ResponseEntity<FloydWarshallResponse> runFloydWarshallAlgorithm(@RequestBody FloydWarshallRequest request) {
        logger.info("Starting Floyd-Warshall algorithm with request: {}", request);
        try {
            var response = aStarService.calculateOptimalPath(request);
            logger.info("Floyd-Warshall algorithm completed successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during Floyd-Warshall algorithm: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/dijkstra")
    public ResponseEntity<DijkstraResponse> runDijkstraAlgorithm(@RequestBody DijkstraRequest request) {
        logger.info("Starting Dijkstra algorithm with request: {}", request);
        try {
            var response = aStarService.calculateOptimalPath(request);
            logger.info("Dijkstra algorithm completed successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during Dijkstra algorithm: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/a-star")
    public ResponseEntity<AStarResponse> runAStarAlgorithm(@RequestBody AStarRequest request) {
        logger.info("Starting A* algorithm with request: {}", request);
        try {
            var response = aStarService.calculateShortestPath(request);
            logger.info("A* algorithm completed successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during A* algorithm: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


