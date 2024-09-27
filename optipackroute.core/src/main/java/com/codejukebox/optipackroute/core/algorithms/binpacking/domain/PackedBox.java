package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class PackedBox {
    private Box box;
    private int x, y, z;

    public PackedBox(Box box, int x, int y, int z) {
        this.box = box;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
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
        return box + " at (" + x + ", " + y + ", " + z + ")";
    }
}

