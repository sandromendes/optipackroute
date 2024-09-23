package com.codejukebox.optipackroute.heuristics.floydwarshall.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codejukebox.optipackroute.common.ConstantsUtil;

public class FloydWarshallEnhancedResult {
	
    public static final int INFINITY = ConstantsUtil.INFINITY; 

    private int[][] distanceMatrix;  // Matriz de distâncias
    private int[][] predecessorMatrix; // Matriz de predecessores
    private List<Integer> path;       // Caminho percorrido
    private int totalCost;            // Custo total
    private HashMap<Integer, List<PathCost>> subPaths = new HashMap<Integer, List<PathCost>>(); 
    
    public FloydWarshallEnhancedResult() {
		super();
	}

	public FloydWarshallEnhancedResult(int[][] distanceMatrix, int[][] predecessorMatrix, List<Integer> path, int totalCost) {
        this.distanceMatrix = distanceMatrix;
        this.predecessorMatrix = predecessorMatrix;
        this.path = path;
        this.totalCost = totalCost;
    }

	public int[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	public void setDistanceMatrix(int[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}

	public int[][] getPredecessorMatrix() {
		return predecessorMatrix;
	}

	public void setPredecessorMatrix(int[][] predecessorMatrix) {
		this.predecessorMatrix = predecessorMatrix;
	}

	public List<Integer> getPath() {
		return path;
	}

	public void setPath(List<Integer> path) {
		this.path = path;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public HashMap<Integer, List<PathCost>> getSubPaths() {
		return subPaths;
	}

	public void setSubPaths(HashMap<Integer, List<PathCost>> subPaths) {
		this.subPaths = subPaths;
	}
	
	/**
	 * This method prints the details of all sub-paths stored in the FloydWarshallResult.
	 * Each sub-path is represented by its origin, destination, and the cost associated with it.
	 *
	 * It iterates over the HashMap that holds the sub-paths, where the key represents 
	 * an identifier for the sub-path (could be an origin node or a path identifier),
	 * and the value is a list of PathCost objects. Each PathCost contains the origin, 
	 * destination, and the sub-cost of a particular leg of the path.
	 *
	 * Example output format:
	 * Sub-paths for path 1:
	 *   Origin: 1, Destination: 2, Cost: 10
	 *   Origin: 2, Destination: 3, Cost: 15
	 *
	 * @param result the FloydWarshallResult object that contains the sub-paths information.
	 */
	public void printSubPaths() {
	    // Iterate through each entry in the subPaths HashMap
	    for (Map.Entry<Integer, List<PathCost>> entry : this.getSubPaths().entrySet()) {
	        Integer pathKey = entry.getKey(); // Key representing the identifier of the sub-path
	        List<PathCost> pathCosts = entry.getValue(); // List of PathCost objects representing each leg of the sub-path
	        
	        // Print the sub-path identifier
	        System.out.println("Sub-paths for path " + pathKey + ":");
	        
	        // Iterate over the list of PathCost objects and print details of each sub-path
	        for (PathCost pathCost : pathCosts) {
	            System.out.println("\tOrigin: " + pathCost.getOrigin() +
	                               ", Destination: " + pathCost.getDestiny() +
	                               ", Cost: " + pathCost.getSubCost());
	        }
	        System.out.println(); // Adds a blank line after each sub-path block for readability
	    }
	}

	public void printPath() {
		
		List<Integer> path = this.getPath();
		
	    if (path == null || path.isEmpty()) {
	        System.out.println("No path found.");
	    } else {
	        String pathString = path.stream()
	                                .map(String::valueOf)
	                                .collect(Collectors.joining(" -> "));
	        System.out.println(pathString);
	    }
	}

	public void printCost() {
	    System.out.println("The smallest cost found was: " + this.getTotalCost());
	}

	public void printDistanceMatrix() {
		printMatrix(distanceMatrix, "Distance");
	}
	
	public void printPredecessorMatrix() {		
		printMatrix(predecessorMatrix, "Predecessor");
	}
	
	private void printMatrix(int[][] matrix, String matrixType) {
	    if (matrix == null || matrix.length == 0) {
	        System.out.println("No data available for " + matrixType + ".");
	        return;
	    }

	    System.out.println(matrixType + ":");
	    int size = matrix.length;
	    for (int i = 0; i < size; i++) {
	        for (int j = 0; j < size; j++) {
	            System.out.print(matrix[i][j] == ConstantsUtil.INFINITY ? "INF" : matrix[i][j]);
	            if (j < size - 1) {
	                System.out.print("\t"); // Separator between columns
	            }
	        }
	        System.out.println(); // Newline at the end of a row
	    }
	}
}
