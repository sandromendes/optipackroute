package com.codejukebox.optipackroute.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.core.common.ConstantsUtil;

public class FloydWarshallEnhancedTest {

	public static final int INF = ConstantsUtil.INFINITY;

	@Test
	void testSmallDistanceMatrix() {
		double[][] matrix = { { 0, 1, INF }, { 1, 0, 1 }, { 1, INF, 0 } };

		var floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
		floydWarshall.runPreProcessor();

		// Node 1 is the start node, and we want to find the path to node 3
		var nodes = new ArrayList<Integer>();
		nodes.add(1);
		nodes.add(2);
		nodes.add(3);

		floydWarshall.findOptimalConfiguration(nodes, nodes.get(0));

		var result = floydWarshall.getResult();

		assertEquals(2, result.getTotalCost(), "Total cost should be 2.");
		assertEquals(Arrays.asList(1, 2, 3), result.getPath(), "Path should be 1 -> 2 -> 3.");
	}

	@Test
	void testFindOptimalConfiguration() {
		double[][] matrix = 
			{ 
			{ 0, 3, 8, INF, 4 }, 
			{ INF, 0, INF, 1, 7 }, 
			{ INF, 4, 0, INF, INF },
			{ 2, INF, 5, 0, INF }, 
			{ INF, INF, INF, 6, 0 } };

		var floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
		floydWarshall.runPreProcessor();

		var nodes = new ArrayList<Integer>();
		nodes.add(1);
		nodes.add(4); // Initial node
		nodes.add(2);
		nodes.add(5);

		floydWarshall.findOptimalConfiguration(nodes, nodes.get(2));

		var result = floydWarshall.getResult();

		// Test Distance Matrix
		double[][] expectedDistanceMatrix = { { 0, 3, 8, 4, 4 }, { 3, 0, 6, 1, 7 }, { 7, 4, 0, 5, 11 },
				{ 2, 5, 5, 0, 6 }, { 8, 11, 11, 6, 0 } };
		assertArrayEquals(expectedDistanceMatrix, result.getDistanceMatrix(),
				"Distance matrix does not match expected.");

		// Test Predecessor Matrix
		double[][] expectedPredecessorMatrix = 
			{ 
			{ INF, 1, 1, 2, 1 }, 
			{ 4, INF, 4, 2, 2 }, 
			{ 4, 3, INF, 2, 2 },
			{ 4, 1, 4, INF, 1 }, 
			{ 4, 1, 4, 5, INF } };
		assertArrayEquals(expectedPredecessorMatrix, result.getPredecessorMatrix(),
				"Predecessor matrix does not match expected.");

		// Test Optimal Path
		var expectedPath = new ArrayList<Integer>();
		expectedPath.add(2);
		expectedPath.add(4);
		expectedPath.add(1);
		expectedPath.add(5);
		assertEquals(expectedPath, result.getPath(), "Optimal path does not match expected.");

		// Test Total Cost
		int expectedTotalCost = 7;
		assertEquals(expectedTotalCost, result.getTotalCost(), "Total cost does not match expected.");
	}
}
