package com.codejukebox.optipackroute.core.samples.router;

import java.util.ArrayList;

import com.codejukebox.optipackroute.core.algorithms.router.floydwarshall.FloydWarshallAlgorithmEnhanced;
import com.codejukebox.optipackroute.core.common.ConstantsUtil;

public class FloydWarshallEnhancedSample50x50 extends FloydWarshallSampleBase {
	public static final int INF = ConstantsUtil.INFINITY;

	public static void main(String[] args) {
		runFloydWarshallEnhanced();
	}

	private static void runFloydWarshallEnhanced() {
		var matrix = getSourceMatrix50x50();

		var floydWarshall = new FloydWarshallAlgorithmEnhanced(matrix);
		floydWarshall.runPreProcessor();

		var nodes = new ArrayList<Integer>();
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

		var result = floydWarshall.getResult();

		printResult(result, false);
	}

	private static double[][] getSourceMatrix50x50() {
		double INF = 99999; // Um valor suficientemente grande para representar "infinito"
		var matrix = new double[50][50];

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				if (i == j) {
					matrix[i][j] = 0; // A distância de um nó para ele mesmo é 0
				} else {
					matrix[i][j] = (int) (Math.random() * 20 + 1); // Valores entre 1 e 20
					if (Math.random() < 0.3) {
						matrix[i][j] = INF; // 30% das vezes, INF para representar ausência de caminho
					}
				}
			}
		}
		return matrix;
	}
}
