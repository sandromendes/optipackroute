package com.codejukebox.optipackroute.core.samples.router;

import java.util.ArrayList;

import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.core.common.ConstantsUtil;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;

public class FloydWarshallEnhancedSample5x5 extends FloydWarshallSampleBase {

	public static final int INF = ConstantsUtil.INFINITY; 
	
    public static void main(String[] args) {
    	runFloydWarshallEnhanced();
    }
    
    private static void runFloydWarshallEnhanced(){        
        int[][] matrix = getSourceMatrix();
        
        FloydWarshallAlgorithmEnhanced floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
        floydWarshall.runPreProcessor();

        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.add(1);
        nodes.add(4); // Initial node
        nodes.add(2);
        nodes.add(5);
        
        floydWarshall.findOptimalConfiguration(nodes, nodes.get(2));
        
        FloydWarshallEnhancedResult result = floydWarshall.getResult();

        printResult(result, false);
    }
    
	private static int[][] getSourceMatrix() {
		int[][] matrix = {
            {0,     3,     8,     INF,   4},
            {INF,   0,     INF,   1,     7},
            {INF,   4,     0,     INF,   INF},
            {2,     INF,   5,     0,     INF},
            {INF,   INF,   INF,   6,     0}
        };
		return matrix;
	}
}
