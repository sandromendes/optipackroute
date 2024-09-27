package com.codejukebox.optipackroute.core.algorithms.router.floydwarshall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.codejukebox.optipackroute.core.common.ConstantsUtil;
import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;
import com.codejukebox.optipackroute.domain.models.floydwarshall.PathCost;

/**
 * This class implements the Floyd-Warshall algorithm to compute the shortest paths between all pairs of nodes
 * in a weighted graph. The algorithm is useful for finding the shortest paths in graphs with positive or negative
 * edge weights but no negative weight cycles.
 * <p>
 * The class performs the following operations:
 * <ul>
 *     <li>Initialize matrices for distance and predecessors.</li>
 *     <li>Compute shortest paths using the Floyd-Warshall algorithm.</li>
 *     <li>Print matrices and optimal paths for visualization.</li>
 *     <li>Handle various edge cases and exceptions related to input data.</li>
 * </ul>
 * <p>
 * 
 * Example usage:
 * 
 * <pre>
 * int[][] adjacencyMatrix = {
 *     {0, 3, INF, INF, INF},
 *     {INF, 0, 1, INF, INF},
 *     {INF, INF, 0, 7, INF},
 *     {2, INF, INF, 0, 1},
 *     {INF, INF, INF, INF, 0}
 * };
 * 
 * FloydWarshallEnhancedV0 floydWarshall = new FloydWarshallEnhancedV0(adjacencyMatrix);
 * floydWarshall.computeShortestPaths(floydWarshall.getDistanceMatrix());
 * FloydWarshallResult result = floydWarshall.getResult();
 * System.out.println("Shortest Path: " + result.getPath());
 * System.out.println("Total Cost: " + result.getTotalCost());
 * 
 * </pre>
 * </p>
 */
public class FloydWarshallAlgorithmEnhanced {
	public static final int INF = ConstantsUtil.INFINITY; // Value representing infinity
	
	private int[][] originalMatrix;
	private int[][] routeCostMatrix;
	private int[][] predecessorMatrix;
	private int[] shortestPath;
	private int[] optimalPath;
	private ArrayList<Integer> path;
	private int[][] cityPairs;
	
	private FloydWarshallEnhancedResult result;

	// Getter e Setter para matrixSize
	private int getMatrixSize() {
		return originalMatrix.length;
	}

	// Getter e Setter para routeCostMatrix
	public int[][] getRouteCostMatrix() {
		return routeCostMatrix;
	}

	public void setRouteCostMatrix(int[][] routeCostMatrix) {
		this.routeCostMatrix = routeCostMatrix;
	}

	// Getter e Setter para predecessorMatrix
	public int[][] getPredecessorMatrix() {
		return predecessorMatrix;
	}

	public void setPredecessorMatrix(int[][] predecessorMatrix) {
		this.predecessorMatrix = predecessorMatrix;
	}

	// Getter e Setter para shortestPath
	public int[] getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(int[] shortestPath) {
		this.shortestPath = shortestPath;
	}

	// Getter e Setter para optimalPath
	public int[] getOptimalPath() {
		return optimalPath;
	}

	public void setOptimalPath(int[] optimalPath) {
		this.optimalPath = optimalPath;
	}

	// Getter e Setter para path
	public ArrayList<Integer> getPath() {
		return path;
	}

	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}

	// Getter e Setter para cityPairs
	public int[][] getCityPairs() {
		return cityPairs;
	}

	public void setCityPairs(int[][] cityPairs) {
		this.cityPairs = cityPairs;
	}

	public FloydWarshallEnhancedResult getResult() {
		return result;
	}

	/**
	 * Constructs a FloydWarshallEnhancedV0 object by initializing the matrices
	 * needed for the Floyd-Warshall algorithm. It validates that the input matrix
	 * is not null, has valid dimensions, and is square. If any of these conditions
	 * are not met, an exception is thrown.
	 *
	 * @param originalMatrix a 2D array representing the adjacency matrix of the
	 *                       graph, where infinity (INF) represents no direct path
	 *                       between vertices.
	 * @throws IllegalArgumentException if the adjacency matrix is null, empty, or
	 *									not square.
	 * 
	 *  Example usage:
	 * 
	 *	<pre>
	 *  	int[][] matrix = { 
	 *  		{ 0, 3, 8, INF, 4 }, 
	 *  		{ INF, 0, INF, 1, 7 }, 
	 *  		{ INF, 4, 0, INF, INF }, 
	 *  		{ 2, INF, 5, 0, INF },		
	 *  		{ INF, INF, INF, 6, 0 } 
	 *  	};
	 * 
	 *  	FloydWarshallEnhancedV0 floydWarshall = new FloydWarshallEnhancedV0(matrix);
	 *  </pre>
	 */
	public FloydWarshallAlgorithmEnhanced(int[][] originalMatrix) {

		if (originalMatrix == null || originalMatrix.length == 0 || originalMatrix[0].length == 0) {
			throw new IllegalArgumentException(
					"Invalid adjacency matrix: it must be non-null and have valid dimensions.");
		}

		// Ensure the matrix is square
		for (int i = 0; i < originalMatrix.length; i++) {
			if (originalMatrix[i].length != originalMatrix.length) {
				throw new IllegalArgumentException("Adjacency matrix must be square.");
			}
		}

		this.originalMatrix = originalMatrix;
		this.routeCostMatrix = new int[originalMatrix.length][originalMatrix.length];
		this.predecessorMatrix = new int[originalMatrix.length][originalMatrix.length];

		result = new FloydWarshallEnhancedResult();
	}

	/**
	 * Executes the Floyd-Warshall algorithm to compute the shortest paths between
	 * all pairs of vertices and returns the result as a FloydWarshallResult object.
	 *
	 * @return FloydWarshallResult containing the cost matrix, total cost, list of
	 *         vertices, and predecessor matrix.
	 * @throws IllegalStateException if the matrices are not properly initialized.
	 */
	public void runPreProcessor() {

		if (routeCostMatrix == null || predecessorMatrix == null) {
			throw new IllegalStateException("Matrices must be initialized before running the algorithm.");
		}
		
		int[][] distanceMatrix = new int[getMatrixSize()][getMatrixSize()];

		initializeMatrices(distanceMatrix);
		computeShortestPaths(distanceMatrix);
	}

	/**
	 * Initializes the distance and predecessor matrices for the Floyd-Warshall
	 * algorithm. The distance matrix is initialized with values from the original
	 * adjacency matrix. The predecessor matrix is also initialized to track the
	 * paths between vertices. If the distance between two vertices is infinity
	 * (INF), or the vertices are the same, the predecessor is set to INF.
	 * Otherwise, the predecessor is set to the current vertex.
	 * 
	 * @param distanceMatrix a 2D array representing the distance between vertices.
	 * @throws IllegalArgumentException if the distanceMatrix is null or not square,
	 *                                  or if its dimensions do not match the
	 *                                  originalMatrix.
	 * 
	 *                                  Example usage:
	 * 
	 *                                  <pre>
	 *                                  int[][] distanceMatrix = new int[5][5];
	 *                                  floydWarshall.initializeMatrices(distanceMatrix);
	 *                                  </pre>
	 */
	private void initializeMatrices(int[][] distanceMatrix) {
		// Check if the input distance matrix is null or has invalid dimensions
		if (distanceMatrix == null || distanceMatrix.length != originalMatrix.length) {
			throw new IllegalArgumentException(
					"Distance matrix must be non-null and have the same dimensions as the original matrix.");
		}

		// Initialize the distance and predecessor matrices
		for (int i = 0; i < distanceMatrix.length; i++) {
			for (int j = 0; j < distanceMatrix.length; j++) {
				// Set distance matrix with values from the original matrix
				distanceMatrix[i][j] = originalMatrix[i][j];

				// Initialize the predecessor matrix based on the distance values
				if (distanceMatrix[i][j] == INF || i == j) {
					predecessorMatrix[i][j] = INF;
				} else if (i != j && distanceMatrix[i][j] < INF) {
					predecessorMatrix[i][j] = i + 1;
				}
			}
		}
	}

	/**
	 * Computes the shortest paths between all pairs of vertices using the
	 * Floyd-Warshall algorithm. The method iteratively updates the distance and
	 * predecessor matrices by checking if a shorter path exists through an
	 * intermediate vertex. If a shorter path is found, the distance matrix and
	 * predecessor matrix are updated accordingly.
	 *
	 * @param distanceMatrix a 2D array representing the distance between vertices.
	 *                       This matrix is updated in place with the shortest path
	 *                       values between all vertex pairs.
	 * @throws IllegalArgumentException if the distanceMatrix is null, its
	 *                                  dimensions do not match the matrix size, or
	 *                                  if it has not been initialized properly.
	 * 
	 *                                  Example usage:
	 * 
	 *                                  <pre>
	 *                                  int[][] distanceMatrix = new int[][] { { 0, 3, INF, INF }, { 2, 0, INF, INF }, { INF, 7, 0, 1 },
	 *                                  		{ 6, INF, INF, 0 } };
	 *                                  floydWarshall.computeShortestPaths(distanceMatrix);
	 *                                  </pre>
	 */
	public void computeShortestPaths(int[][] distanceMatrix) {
		// Check if the input distance matrix is null or has invalid dimensions
		if (distanceMatrix == null || distanceMatrix.length != getMatrixSize()) {
			throw new IllegalArgumentException("Distance matrix must be non-null and match the matrix size.");
		}

		// Compute the shortest paths between all pairs of vertices
		for (int k = 0; k < getMatrixSize(); k++) {
			for (int i = 0; i < getMatrixSize(); i++) {
				for (int j = 0; j < getMatrixSize(); j++) {
					// Update distance and predecessor matrices if a shorter path is found
					if (distanceMatrix[i][j] > distanceMatrix[i][k] + distanceMatrix[k][j]) {
						distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
						predecessorMatrix[i][j] = predecessorMatrix[k][j];
					}
				}
			}

			// Update the original matrix with the current distance matrix state
			originalMatrix = distanceMatrix;
		}

		// Update the route cost matrix and result object with the final distance and
		// predecessor matrices
		routeCostMatrix = distanceMatrix;
		result.setDistanceMatrix(distanceMatrix);
		result.setPredecessorMatrix(predecessorMatrix);
	}

	private HashMap<Integer, List<PathCost>> iterations = new HashMap<Integer, List<PathCost>>(); 
	
	/**
	 * Prints the optimal path and its cost from a given list of city elements,
	 * starting from a specified city. It generates permutations of the cities to
	 * find the path with the minimum total cost. The optimal path and its
	 * associated cost are then displayed.
	 * 
	 * @param elements  The list of cities to consider for the path.
	 * @param startCity The city from which the path computation starts.
	 * @throws IllegalArgumentException if the list of elements is null, empty, or
	 *                                  contains invalid city indices.
	 * @throws IllegalStateException    if routeCostMatrix or result is not properly
	 *                                  initialized.
	 * 
	 *                                  Example usage:
	 * 
	 *                                  <pre>
	 *                                  ArrayList<Integer> cities = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
	 *                                  findOptimalConfiguration(cities, 1); // Prints the optimal path starting from city 1
	 *                                  </pre>
	 */
	public void findOptimalConfiguration(ArrayList<Integer> elements, Integer startCity) {
		// Validate inputs
		if (elements == null || elements.isEmpty()) {
			throw new IllegalArgumentException("Elements list must not be null or empty.");
		}
		if (startCity == null || !elements.contains(startCity)) {
			throw new IllegalArgumentException("Start city must be part of the elements list.");
		}
		if (routeCostMatrix == null || routeCostMatrix.length == 0 || routeCostMatrix[0].length == 0) {
			throw new IllegalStateException("Route cost matrix is not properly initialized.");
		}
		if (result == null) {
			throw new IllegalStateException("Result object is not initialized.");
		}
		
	    // Measure start time
	    long startTime = System.nanoTime();
		
		// Initialize variables
		PermutationGenerator generator = new PermutationGenerator(elements.size());
		int[] indices;
		int cost = 0;
		int partialCost;
		int minCost = INF;
		shortestPath = new int[elements.size()];
		optimalPath = new int[elements.size()];
		path = new ArrayList<>();
		cityPairs = new int[elements.size()][2];

		int key = 0;
		
		// Generate permutations and calculate costs
		while (generator.hasMore()) {
			indices = generator.getNext();
			
			key++;
			List<PathCost> paths = new ArrayList<PathCost>();
			
			for (int i = 0; i < indices.length - 1; i++) {		
				cityPairs[i][0] = elements.get(indices[i]);
				cityPairs[i][1] = elements.get(indices[i + 1]);

				if (shortestPath.length == indices.length) {
					shortestPath[i] = cityPairs[i][0];
					shortestPath[i + 1] = cityPairs[i][1];
				}

				partialCost = routeCostMatrix[cityPairs[i][0] - 1][cityPairs[i][1] - 1] != INF
						? routeCostMatrix[cityPairs[i][0] - 1][cityPairs[i][1] - 1]
						: 0;

				cost += partialCost;

				paths.add(new PathCost(cityPairs[i][0], cityPairs[i][1], partialCost));
				
				if ((i + 1) % (elements.size() - 1) == 0) {
					if (cost < minCost) {
						minCost = cost;
						System.arraycopy(shortestPath, 0, optimalPath, 0, shortestPath.length);
					}
					
					cost = 0;
				}
			}
			
			iterations.put(key, paths);
		}

		result.setSubPaths(iterations);
		
		List<Integer> optimalPathList = Arrays.stream(optimalPath).boxed().collect(Collectors.toList());
		result.setPath(optimalPathList);
		result.setTotalCost(minCost);
		
		// Measure end time and calculate duration
	    long endTime = System.nanoTime();
	    long durationInNanoseconds = endTime - startTime;
	    double durationInMilliseconds = durationInNanoseconds / 1_000_000.0;
	    
	    // Print the processing time
	    System.out.printf("Processing time: %.2f milliseconds%n", durationInMilliseconds);
	}
}