package com.codejukebox.optipackroute.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RouteCalculationService aStarService;

    @PostMapping("/floyd-warshall")
    public ResponseEntity<FloydWarshallResponse> runFloydWarshallAlgorithm(@RequestBody FloydWarshallRequest request) {
        var response = aStarService.calculateOptimalPath(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/dijkstra")
    public ResponseEntity<DijkstraResponse> runDijkstraAlgorithm(@RequestBody DijkstraRequest request) {
        var response = aStarService.calculateOptimalPath(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/a-star")
    public ResponseEntity<AStarResponse> runAStarAlgorithm(@RequestBody AStarRequest request) {
        var response = aStarService.calculateShortestPath(request);
        return ResponseEntity.ok(response);
    }
}

