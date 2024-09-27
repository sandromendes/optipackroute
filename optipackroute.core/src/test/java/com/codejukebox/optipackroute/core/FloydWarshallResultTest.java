package com.codejukebox.optipackroute.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.codejukebox.optipackroute.domain.models.floydwarshall.FloydWarshallEnhancedResult;
import com.codejukebox.optipackroute.domain.models.floydwarshall.PathCost;

public class FloydWarshallResultTest {

    private FloydWarshallEnhancedResult result;

    @BeforeEach
    void setUp() {
        result = new FloydWarshallEnhancedResult();
    }

    @Test
    void testSubPathsEmpty() {
        // Test when subPaths is empty
        HashMap<Integer, List<PathCost>> subPaths = result.getSubPaths();
        assertTrue(subPaths.isEmpty(), "Sub-paths should be empty.");
    }

    @Test
    void testAddSingleSubPath() {
        // Test adding a single sub-path
        List<PathCost> pathList = new ArrayList<>();
        pathList.add(new PathCost(1, 2, 10));
        
        result.getSubPaths().put(1, pathList);
        
        // Check if subPaths contains the added path
        assertEquals(1, result.getSubPaths().size(), "There should be one path.");
        List<PathCost> retrievedPath = result.getSubPaths().get(1);
        assertEquals(1, retrievedPath.get(0).getOrigin(), "Origin should be 1.");
        assertEquals(2, retrievedPath.get(0).getDestiny(), "Destination should be 2.");
        assertEquals(10, retrievedPath.get(0).getSubCost(), "SubCost should be 10.");
    }

    @Test
    void testAddMultipleSubPaths() {
        // Test adding multiple sub-paths
        List<PathCost> pathList1 = new ArrayList<>();
        pathList1.add(new PathCost(1, 2, 10));
        pathList1.add(new PathCost(2, 3, 20));
        
        List<PathCost> pathList2 = new ArrayList<>();
        pathList2.add(new PathCost(3, 4, 15));
        
        result.getSubPaths().put(1, pathList1);
        result.getSubPaths().put(2, pathList2);
        
        // Check the size of subPaths
        assertEquals(2, result.getSubPaths().size(), "There should be two paths.");
        
        // Verify details of the first path
        List<PathCost> retrievedPath1 = result.getSubPaths().get(1);
        assertEquals(2, retrievedPath1.size(), "First path should have 2 segments.");
        assertEquals(10, retrievedPath1.get(0).getSubCost(), "First segment cost should be 10.");
        assertEquals(20, retrievedPath1.get(1).getSubCost(), "Second segment cost should be 20.");
        
        // Verify details of the second path
        List<PathCost> retrievedPath2 = result.getSubPaths().get(2);
        assertEquals(1, retrievedPath2.size(), "Second path should have 1 segment.");
        assertEquals(15, retrievedPath2.get(0).getSubCost(), "Cost should be 15.");
    }

    @Test
    void testZeroCostPath() {
        // Test a sub-path where the cost is zero
        List<PathCost> pathList = new ArrayList<>();
        pathList.add(new PathCost(1, 1, 0)); // Cost is zero since it's the same city
        
        result.getSubPaths().put(1, pathList);
        
        List<PathCost> retrievedPath = result.getSubPaths().get(1);
        assertEquals(0, retrievedPath.get(0).getSubCost(), "Cost for the same city should be 0.");
    }

    @Test
    void testLargeSubCost() {
        // Test a sub-path with large costs
        List<PathCost> pathList = new ArrayList<>();
        pathList.add(new PathCost(1, 2, Integer.MAX_VALUE));
        
        result.getSubPaths().put(1, pathList);
        
        List<PathCost> retrievedPath = result.getSubPaths().get(1);
        assertEquals(Integer.MAX_VALUE, retrievedPath.get(0).getSubCost(), "Cost should be Integer.MAX_VALUE.");
    }

    @Test
    void testNegativeCost() {
        // Test a sub-path with negative costs
        List<PathCost> pathList = new ArrayList<>();
        pathList.add(new PathCost(1, 2, -5));
        
        result.getSubPaths().put(1, pathList);
        
        List<PathCost> retrievedPath = result.getSubPaths().get(1);
        assertEquals(-5, retrievedPath.get(0).getSubCost(), "Sub-cost should be -5.");
    }

    @Test
    void testPrintSubPaths() {
        // Test printSubPaths method by checking console output
        List<PathCost> pathList = new ArrayList<>();
        pathList.add(new PathCost(1, 2, 10));
        pathList.add(new PathCost(2, 3, 15));
        result.getSubPaths().put(1, pathList);

        // Call the method and visually verify the output for now
        // Alternatively, use a library like SystemLambda to capture console output
        result.printSubPaths();
    }
}

