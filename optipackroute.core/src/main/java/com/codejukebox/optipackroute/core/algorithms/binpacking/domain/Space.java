package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class Space {
    private int x, y, z;  // Coordenadas do canto inferior esquerdo do espaço
    private int length, height, depth;  // Dimensões do espaço disponível

    public Space(int x, int y, int z, int length, int height, int depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = length;
        this.height = height;
        this.depth = depth;
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

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "Space{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", length=" + length +
                ", height=" + height +
                ", depth=" + depth +
                '}';
    }
}
