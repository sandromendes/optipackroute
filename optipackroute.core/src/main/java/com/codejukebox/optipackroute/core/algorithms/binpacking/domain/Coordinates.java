package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class Coordinates {
    private int x;
    private int y;
    private int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
