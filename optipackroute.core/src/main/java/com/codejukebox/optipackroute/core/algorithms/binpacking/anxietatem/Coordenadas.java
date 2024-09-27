package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

public class Coordenadas {

    private int x;
    private int y;
    private int z;
    private int nulo = -1;

    public Coordenadas(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = nulo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "x: " + Integer.toString(x) + " y: " + Integer.toString(y) + " z: " + Integer.toString(z);
    }
}
