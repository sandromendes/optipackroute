package com.codejukebox.optipackroute.core.samples.router;

import java.util.ArrayList;

import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.core.common.ConstantsUtil;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;

public class FloydWarshallEnhancedSample3x3 extends FloydWarshallSampleBase{

	public static final int INF = ConstantsUtil.INFINITY; 
	
    public static void main(String[] args) {
    	runFloydWarshallEnhancedMinimun();
    }
    
    private static void runFloydWarshallEnhancedMinimun() {
	    int[][] matrix = {
		        {0, 	1, 		INF},
		        {1, 	0, 		1},
		        {1, 	INF, 	0}
		    };

		    FloydWarshallAlgorithmEnhanced floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
		    floydWarshall.runPreProcessor();

		    // Node 1 is the start node, and we want to find the path to node 3
		    ArrayList<Integer> nodes = new ArrayList<>();
		    nodes.add(1);
		    nodes.add(2);
		    nodes.add(3);

		    floydWarshall.findOptimalConfiguration(nodes, nodes.get(0));

	        FloydWarshallEnhancedResult result = floydWarshall.getResult();

	        printResult(result, false);
    }
}
