package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class CornerPoint {

    private Coordinates coordinates;
    private Box box;

    public CornerPoint() {
        this.box = null;
    }

    public CornerPoint(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    
    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object obj) {

        boolean result = false;

        if (obj instanceof CornerPoint) {
            CornerPoint point = (CornerPoint) obj;

            if ((point.getCoordinates().getX() == getCoordinates().getX())
                    && (point.getCoordinates().getY() == getCoordinates().getY())
                    && (point.getCoordinates().getZ() == getCoordinates().getZ())) {
                result = true;
            }
        }

        return result;
    }
    
    public void printDetails() {
        String coordinatesStr = (coordinates != null) ? coordinates.toString() : "Coordinates not defined";
        String boxStr = (box != null) ? box.toString() : "Box not defined";

        System.out.println(String.format("CornerPoint Details:\n- Coordinates: %s\n- Box: %s", coordinatesStr, boxStr));
    }
}

