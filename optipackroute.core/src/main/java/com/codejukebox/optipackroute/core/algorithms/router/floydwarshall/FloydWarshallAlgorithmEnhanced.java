package com.codejukebox.optipackroute.core.algorithms.router.floydwarshall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(FloydWarshallAlgorithmEnhanced.class);
	
	public static final int INF = ConstantsUtil.INFINITY; // Value representing infinity
	
	private double[][] originalMatrix;
	private double[][] routeCostMatrix;
	private double[][] predecessorMatrix;
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
	public double[][] getRouteCostMatrix() {
		return routeCostMatrix;
	}

	public void setRouteCostMatrix(double[][] routeCostMatrix) {
		this.routeCostMatrix = routeCostMatrix;
	}

	// Getter e Setter para predecessorMatrix
	public double[][] getPredecessorMatrix() {
		return predecessorMatrix;
	}

	public void setPredecessorMatrix(double[][] predecessorMatrix) {
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
    public FloydWarshallAlgorithmEnhanced(double[][] originalMatrix) {
        logger.info("Initializing FloydWarshallAlgorithmEnhanced with provided adjacency matrix.");

        if (originalMatrix == null || originalMatrix.length == 0 || originalMatrix[0].length == 0) {
            logger.error("Invalid adjacency matrix: it must be non-null and have valid dimensions.");
            throw new IllegalArgumentException(
                    "Invalid adjacency matrix: it must be non-null and have valid dimensions.");
        }

        // Ensure the matrix is square
        for (int i = 0; i < originalMatrix.length; i++) {
            if (originalMatrix[i].length != originalMatrix.length) {
                logger.error("Adjacency matrix must be square.");
                throw new IllegalArgumentException("Adjacency matrix must be square.");
            }
        }

        this.originalMatrix = originalMatrix;
        logger.debug("Original matrix set successfully.");

        this.routeCostMatrix = new double[originalMatrix.length][originalMatrix.length];
        logger.debug("Route cost matrix initialized with dimensions: {}", originalMatrix.length);

        this.predecessorMatrix = new double[originalMatrix.length][originalMatrix.length];
        logger.debug("Predecessor matrix initialized with dimensions: {}", originalMatrix.length);

        result = new FloydWarshallEnhancedResult();
        logger.info("FloydWarshallAlgorithmEnhanced initialization completed successfully.");
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
        logger.info("Starting the pre-processing step.");

        if (routeCostMatrix == null || predecessorMatrix == null) {
            logger.error("Matrices must be initialized before running the algorithm.");
            throw new IllegalStateException("Matrices must be initialized before running the algorithm.");
        }

        logger.debug("Matrices are initialized, proceeding with distance matrix creation.");

        double[][] distanceMatrix = new double[getMatrixSize()][getMatrixSize()];
        logger.debug("Distance matrix initialized with size: {}", getMatrixSize());

        initializeMatrices(distanceMatrix);
        logger.info("Matrices initialized successfully.");

        computeShortestPaths(distanceMatrix);
        logger.info("Shortest paths computed successfully.");
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
    private void initializeMatrices(double[][] distanceMatrix) {
        logger.info("Initializing matrices.");

        // Check if the input distance matrix is null or has invalid dimensions
        if (distanceMatrix == null || distanceMatrix.length != originalMatrix.length) {
            logger.error("Distance matrix must be non-null and have the same dimensions as the original matrix.");
            throw new IllegalArgumentException(
                    "Distance matrix must be non-null and have the same dimensions as the original matrix.");
        }

        logger.debug("Distance matrix dimensions are valid. Proceeding with initialization.");

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

                logger.debug("Set distanceMatrix[{}][{}] = {} and predecessorMatrix[{}][{}] = {}",
                        i, j, distanceMatrix[i][j], i, j, predecessorMatrix[i][j]);
            }
        }

        logger.info("Matrices initialized successfully.");
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
    public void computeShortestPaths(double[][] distanceMatrix) {
        logger.info("Computing shortest paths.");

        // Check if the input distance matrix is null or has invalid dimensions
        if (distanceMatrix == null || distanceMatrix.length != getMatrixSize()) {
            logger.error("Distance matrix must be non-null and match the matrix size.");
            throw new IllegalArgumentException("Distance matrix must be non-null and match the matrix size.");
        }

        logger.debug("Starting to compute shortest paths with matrix size: {}", getMatrixSize());

        // Compute the shortest paths between all pairs of vertices
        for (int k = 0; k < getMatrixSize(); k++) {
            for (int i = 0; i < getMatrixSize(); i++) {
                for (int j = 0; j < getMatrixSize(); j++) {
                    // Update distance and predecessor matrices if a shorter path is found
                    if (distanceMatrix[i][j] > distanceMatrix[i][k] + distanceMatrix[k][j]) {
                        logger.debug("Found shorter path: distanceMatrix[{}][{}] > distanceMatrix[{}][{}] + distanceMatrix[{}][{}]",
                                i, j, i, k, k, j);
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                        predecessorMatrix[i][j] = predecessorMatrix[k][j];
                    }
                }
            }

            // Update the original matrix with the current distance matrix state
            originalMatrix = distanceMatrix;
            logger.debug("Updated originalMatrix with current distance matrix state after iteration {}", k);
        }

        // Update the route cost matrix and result object with the final distance and predecessor matrices
        routeCostMatrix = distanceMatrix;
        result.setDistanceMatrix(distanceMatrix);
        result.setPredecessorMatrix(predecessorMatrix);

        logger.info("Shortest path computation completed successfully.");
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
        logger.info("Finding optimal configuration for the given elements.");

        // Validate inputs
        if (elements == null || elements.isEmpty()) {
            logger.error("Elements list must not be null or empty.");
            throw new IllegalArgumentException("Elements list must not be null or empty.");
        }
        if (startCity == null || !elements.contains(startCity)) {
            logger.error("Start city must be part of the elements list.");
            throw new IllegalArgumentException("Start city must be part of the elements list.");
        }
        if (routeCostMatrix == null || routeCostMatrix.length == 0 || routeCostMatrix[0].length == 0) {
            logger.error("Route cost matrix is not properly initialized.");
            throw new IllegalStateException("Route cost matrix is not properly initialized.");
        }
        if (result == null) {
            logger.error("Result object is not initialized.");
            throw new IllegalStateException("Result object is not initialized.");
        }
        
        // Measure start time
        long startTime = System.nanoTime();
        logger.info("Start processing for optimal configuration at {}", startTime);
        
        // Initialize variables
        var generator = new PermutationGenerator(elements.size());
        int[] indices;
        double cost = 0;
        double partialCost;
        double minCost = INF;
        shortestPath = new int[elements.size()];
        optimalPath = new int[elements.size()];
        path = new ArrayList<>();
        cityPairs = new int[elements.size()][2];

        int key = 0;
        
        // Generate permutations and calculate costs
        while (generator.hasMore()) {
            indices = generator.getNext();
            key++;
            logger.debug("Processing permutation with key: {}", key);
            var paths = new ArrayList<PathCost>();
            
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
                logger.debug("Current cost for city pair [{} -> {}]: {}", cityPairs[i][0], cityPairs[i][1], partialCost);

                paths.add(new PathCost(cityPairs[i][0], cityPairs[i][1], partialCost));
                
                if ((i + 1) % (elements.size() - 1) == 0) {
                    if (cost < minCost) {
                        minCost = cost;
                        System.arraycopy(shortestPath, 0, optimalPath, 0, shortestPath.length);
                        logger.info("New optimal path found with cost: {}", minCost);
                    }
                    cost = 0;
                }
            }
            
            iterations.put(key, paths);
        }

        result.setSubPaths(iterations);
        
        var optimalPathList = Arrays.stream(optimalPath).boxed().collect(Collectors.toList());
        result.setPath(optimalPathList);
        result.setTotalCost(minCost);
        
        // Measure end time and calculate duration
        long endTime = System.nanoTime();
        long durationInNanoseconds = endTime - startTime;
        double durationInMilliseconds = durationInNanoseconds / 1_000_000.0;
        
        // Print the processing time
        logger.info("Processing time: {:.2f} milliseconds", durationInMilliseconds);
    }
}