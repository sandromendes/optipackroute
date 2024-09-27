package com.codejukebox.optipackroute.core.samples.router;

import java.util.ArrayList;

import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.core.common.ConstantsUtil;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;

public class FloydWarshallEnhancedSample50x50 extends FloydWarshallSampleBase {
public static final int INF = ConstantsUtil.INFINITY; 
	
    public static void main(String[] args) {
    	runFloydWarshallEnhanced();
    }
    
    private static void runFloydWarshallEnhanced(){        
        int[][] matrix = getSourceMatrix50x50();
        
        FloydWarshallAlgorithmEnhanced floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
        floydWarshall.runPreProcessor();

        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.add(1);
        nodes.add(4); // Initial node
        nodes.add(7);
        nodes.add(15);
        nodes.add(14);
        nodes.add(10);
        nodes.add(3);
        nodes.add(19);
        nodes.add(8);
        
        floydWarshall.findOptimalConfiguration(nodes, nodes.get(2));
        
        FloydWarshallEnhancedResult result = floydWarshall.getResult();

        printResult(result, false);
    }
    
    private static int[][] getSourceMatrix50x50() {
        int INF = 99999; // Um valor suficientemente grande para representar "infinito"
        int[][] matrix = new int[50][50];

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (i == j) {
                    matrix[i][j] = 0; // A distância de um nó para ele mesmo é 0
                } else {
                    matrix[i][j] = (int) (Math.random() * 20 + 1); // Valores entre 1 e 20
                    if (Math.random() < 0.3) {
                        matrix[i][j] = INF; // 30% das vezes, INF para representar ausência de caminho
                    }
                }
            }
        }
        return matrix;
    }
}
