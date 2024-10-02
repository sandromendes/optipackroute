package com.codejukebox.optipackroute.core.samples.binpacking;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.AnxietatemAlgorithm;
import com.codejukebox.optipackroute.domain.models.binpacking.CornerPoint;
import com.codejukebox.optipackroute.domain.models.binpacking.Dimensions;

public class AnxietatemSample {
	
	public static void main(String[] args) {        
	    var dimensions = new Dimensions(100, 40, 20);
	    
	    long start = System.currentTimeMillis();
	    
	    var packer = new AnxietatemAlgorithm(dimensions, 200);
	    var result = packer.pack();
	    
	    for (CornerPoint item : result.getUsedCornerPoints()) {
	        System.out.println();
	        item.printDetails();
	    }
	    
	    long end = System.currentTimeMillis();

	    System.out.println("Elapsed time: " + (end - start) + "ms");
	}
}
