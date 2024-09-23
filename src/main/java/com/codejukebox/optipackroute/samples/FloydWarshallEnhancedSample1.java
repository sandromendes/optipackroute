package com.codejukebox.optipackroute.samples;

import java.util.ArrayList;

import com.codejukebox.optipackroute.common.ConstantsUtil;
import com.codejukebox.optipackroute.heuristics.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.heuristics.floydwarshall.domain.FloydWarshallEnhancedResult;

public class FloydWarshallEnhancedSample1 {

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

	        printResult(result);
    }
	
	private static void printResult(FloydWarshallEnhancedResult result) {
        System.out.println("Optimal Path:");
        result.printPath();

        System.out.println("\nTotal Cost:");
        result.printCost();

        System.out.println("\nDistance Matrix:\n");
        result.printDistanceMatrix();

        System.out.println("\nPredecessor Matrix:\n");
        result.printPredecessorMatrix();
        
        System.out.println("\nSub-Paths:\n");
        result.printSubPaths();
        
        System.out.println("\nFloyd-Warshall Enhanced execution completed.");
	}
}
