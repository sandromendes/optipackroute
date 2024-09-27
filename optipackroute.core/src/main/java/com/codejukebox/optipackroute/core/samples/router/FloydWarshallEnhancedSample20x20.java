package com.codejukebox.optipackroute.core.samples.router;

import java.util.ArrayList;

import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.core.common.ConstantsUtil;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;

public class FloydWarshallEnhancedSample20x20 extends FloydWarshallSampleBase{
	public static final int INF = ConstantsUtil.INFINITY; 
	
    public static void main(String[] args) {
    	runFloydWarshallEnhanced();
    }
    
    private static void runFloydWarshallEnhanced(){        
        int[][] matrix = getSourceMatrix20x20();
        
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
    
    private static int[][] getSourceMatrix20x20() {
        int[][] matrix = {
            {0,     3,     8,     INF,   4,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   0,     INF,   1,     7,     INF,   2,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   4,     0,     INF,   INF,   5,     INF,   INF,   9,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {2,     INF,   5,     0,     INF,   INF,   INF,   3,     INF,   6,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   INF,   6,     0,     INF,   INF,   INF,   4,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   8,     INF,   INF,   3,     0,     INF,   INF,   5,     INF,   1,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   INF,   INF,   INF,   7,     0,     INF,   INF,   INF,   INF,   2,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   4,     INF,   INF,   INF,   INF,   0,     INF,   INF,   6,     INF,   INF,   1,     INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   INF,   2,     INF,   INF,   INF,   INF,   0,     8,     INF,   INF,   INF,   INF,   3,     INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   INF,   INF,   INF,   5,     INF,   7,     INF,   0,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   INF,   INF,   6,     INF,   INF,   INF,   INF,   INF,   0,     INF,   9,     INF,   2,     INF,   INF,   INF,   INF,   INF},
            {INF,   INF,   INF,   7,     INF,   INF,   INF,   INF,   INF,   3,     INF,   0,     INF,   INF,   INF,   4,     INF,   INF,   INF,   INF},
            {4,     INF,   INF,   INF,   8,     INF,   INF,   6,     INF,   INF,   INF,   INF,   0,     INF,   INF,   3,     INF,   INF,   INF,   INF},
            {INF,   INF,   9,     INF,   INF,   INF,   INF,   INF,   1,     INF,   INF,   INF,   INF,   0,     INF,   INF,   2,     INF,   INF,   INF},
            {INF,   6,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   5,     INF,   INF,   0,     INF,   INF,   INF,   3,     INF},
            {INF,   INF,   INF,   4,     INF,   9,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   6,     0,     INF,   5,     INF,   INF},
            {INF,   INF,   INF,   INF,   INF,   INF,   3,     INF,   INF,   INF,   INF,   INF,   7,     INF,   INF,   INF,   0,     INF,   INF,   2},
            {INF,   INF,   2,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   4,     INF,   INF,   7,     INF,   8,     INF,   0,     INF,   INF},
            {INF,   INF,   INF,   8,     INF,   INF,   INF,   INF,   INF,   INF,   INF,   3,     INF,   4,     INF,   INF,   INF,   INF,   0,     7},
            {INF,   INF,   INF,   INF,   INF,   INF,   INF,   INF,   9,     INF,   INF,   INF,   5,     INF,   INF,   INF,   INF,   INF,   INF,   0}
        };
        return matrix;
    }
}
