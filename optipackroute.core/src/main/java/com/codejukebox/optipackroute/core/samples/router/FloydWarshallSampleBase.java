package com.codejukebox.optipackroute.core.samples.router;

import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;

public class FloydWarshallSampleBase {
	protected static void printResult(FloydWarshallEnhancedResult result, boolean isSubPathsPrinted) {
        System.out.println("Optimal Path:");
        result.printPath();

        System.out.println("\nTotal Cost:");
        result.printCost();

        System.out.println("\nDistance Matrix:\n");
        result.printDistanceMatrix();

        System.out.println("\nPredecessor Matrix:\n");
        result.printPredecessorMatrix();
        
        if(isSubPathsPrinted) {
        	System.out.println("\nSub-Paths:\n");
        	result.printSubPaths();        	
        }
        
        System.out.println("\nFloyd-Warshall Enhanced execution completed.");
	}
}
