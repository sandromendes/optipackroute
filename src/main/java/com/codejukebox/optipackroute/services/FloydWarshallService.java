package com.codejukebox.optipackroute.services;

import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.dtos.floydwarshall.FloydWarshallRequest;
import com.codejukebox.optipackroute.dtos.floydwarshall.FloydWarshallResponse;
import com.codejukebox.optipackroute.heuristics.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.heuristics.floydwarshall.domain.FloydWarshallEnhancedResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class FloydWarshallService {

    public FloydWarshallResponse calculateOptimalPath(FloydWarshallRequest request) {
        int[][] matrix = request.getMatrix();
        List<Integer> nodes = request.getNodes();
        int initialNode = request.getInitialNode();

        FloydWarshallAlgorithmEnhanced floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
        floydWarshall.runPreProcessor();

        // Converte a lista de inteiros para um ArrayList
        ArrayList<Integer> nodesList = new ArrayList<>(nodes);
        floydWarshall.findOptimalConfiguration(nodesList, initialNode);

        // Obtém o resultado
        FloydWarshallEnhancedResult result = floydWarshall.getResult();

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