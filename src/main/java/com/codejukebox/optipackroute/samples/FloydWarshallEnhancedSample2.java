package com.codejukebox.optipackroute.samples;

import java.util.ArrayList;

import com.codejukebox.optipackroute.common.ConstantsUtil;
import com.codejukebox.optipackroute.heuristics.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.heuristics.floydwarshall.domain.FloydWarshallEnhancedResult;

public class FloydWarshallEnhancedSample2 {

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

        printResult(result);
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
