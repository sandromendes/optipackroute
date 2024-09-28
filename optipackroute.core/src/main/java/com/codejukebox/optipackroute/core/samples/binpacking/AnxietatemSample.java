package com.codejukebox.optipackroute.core.samples.binpacking;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.AnxietatemAlgorithm;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.Coordinates;
import com.codejukebox.optipackroute.domain.models.binpacking.CornerPoint;
import com.codejukebox.optipackroute.domain.models.binpacking.Dimensions;

public class AnxietatemSample {
	
	public static void main(String[] args) {        
	    var dimensions = new Dimensions(100, 40, 20);
	    
	    var container = new Box();
	    container.setDimensions(dimensions);
	    
	    var coordinates = new Coordinates(0, 0, 0);
	    
	    var cornerPoint = new CornerPoint();
	    cornerPoint.setCoordinates(coordinates);
	    
	    long start = System.currentTimeMillis();
	    
	    var packer = new AnxietatemAlgorithm();
	    var result = packer.pack(container, cornerPoint, 10);
	    
	    for (CornerPoint item : result) {
	        System.out.println();
	        item.printDetails();
	    }
	    
	    long end = System.currentTimeMillis();

	    System.out.println("Elapsed time: " + (end - start) + "ms");
	}
}
