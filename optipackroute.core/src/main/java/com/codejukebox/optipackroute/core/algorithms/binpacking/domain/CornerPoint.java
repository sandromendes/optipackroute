package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class CornerPoint {
    private Coordinates coordinates;
    private Box box;

    public CornerPoint(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Box getBox() {
        return box;
    }

    @Override
    public String toString() {
        return "CornerPoint at (" + coordinates.getX() + ", " + coordinates.getY() + ", " + coordinates.getZ() + ")";
    }
}

