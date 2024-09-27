package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class Dimensions {
    private int length;
    private int height;
    private int depth;

    public Dimensions(int length, int height, int depth) {
        this.length = length;
        this.height = height;
        this.depth = depth;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }
    
    public int getVolume() {
        return length * height * depth;
    }
}

