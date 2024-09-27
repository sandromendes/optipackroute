package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class Box {
    private String label;
    private Dimensions dimensions;

    public Box(String id, int length, int height, int depth) {
        this.label = id;
        this.dimensions = new Dimensions(length, height, depth);
    }

    public String getLabel() {
        return label;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
    
    public int getVolume() {
        return dimensions.getLength() * dimensions.getHeight() * dimensions.getDepth();
    }

    @Override
    public String toString() {
        return "Box " + label + " [length=" + dimensions.getLength() + ", height=" + dimensions.getHeight() + ", depth=" + dimensions.getDepth() + "]";
    }
}
