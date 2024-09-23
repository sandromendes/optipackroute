package com.codejukebox.optipackroute.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codejukebox.optipackroute.dtos.astar.AStarRequest;
import com.codejukebox.optipackroute.dtos.astar.AStarResponse;
import com.codejukebox.optipackroute.dtos.dijkstra.DijkstraRequest;
import com.codejukebox.optipackroute.dtos.dijkstra.DijkstraResponse;
import com.codejukebox.optipackroute.dtos.floydwarshall.FloydWarshallRequest;
import com.codejukebox.optipackroute.dtos.floydwarshall.FloydWarshallResponse;
import com.codejukebox.optipackroute.services.AStarService;
import com.codejukebox.optipackroute.services.DijkstraService;
import com.codejukebox.optipackroute.services.FloydWarshallService;

@RestController
@RequestMapping("/api/v1/router/")
public class RouterController {

    @Autowired
    private FloydWarshallService floydWarshallService;

    @PostMapping("/floyd-warshall")
    public ResponseEntity<FloydWarshallResponse> runFloydWarshallAlgorithm(@RequestBody FloydWarshallRequest request) {
        FloydWarshallResponse response = floydWarshallService.calculateOptimalPath(request);
        return ResponseEntity.ok(response);
    }
    
    @Autowired
    private DijkstraService dijkstraService;
    
    @PostMapping("/dijkstra")
    public ResponseEntity<DijkstraResponse> runDijkstraAlgorithm(@RequestBody DijkstraRequest request) {
        DijkstraResponse response = dijkstraService.calculateOptimalPath(request);
        return ResponseEntity.ok(response);
    }
    
    @Autowired
    private AStarService aStarService;
    
    @PostMapping("/a-star")
    public ResponseEntity<AStarResponse> runAStarAlgorithm(@RequestBody AStarRequest request) {
        AStarResponse response = aStarService.calculateShortestPath(request);
        return ResponseEntity.ok(response);
    }
}

