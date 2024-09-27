package com.codejukebox.optipackroute.core.samples.binpacking;

import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.AnxietatemAlgorithm;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Box;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Coordinates;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.CornerPoint;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Dimensions;

public class AnxietatemSample {
	
	public static void main(String[] args) {        
	    Dimensions dimensions = new Dimensions(100, 40, 20);
	    
	    Box container = new Box();
	    container.setDimensions(dimensions);
	    
	    Coordinates coordinates = new Coordinates(0, 0, 0);
	    
	    CornerPoint cornerPoint = new CornerPoint();
	    cornerPoint.setCoordinates(coordinates);
	    
	    long start = System.currentTimeMillis();
	    
	    AnxietatemAlgorithm packer = new AnxietatemAlgorithm();
	    List<CornerPoint> result = packer.pack(container, cornerPoint, 10);
	    
	    for (CornerPoint item : result) {
	        System.out.println();
	        item.printDetails();
	    }
	    
	    long end = System.currentTimeMillis();

	    System.out.println("Elapsed time: " + (end - start) + "ms");
	}
}
