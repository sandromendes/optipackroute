package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

import java.util.ArrayList;

import com.codejukebox.optipackroute.domain.models.binpacking.CornerPoint;

/**
 * Class responsible for cleaning up duplicate or redundant corner points.
 */
public class GarbagePoint {

    /**
     * Constructs a new instance of GarbagePoint.
     */
    public GarbagePoint() {
    }

    /**
     * Removes redundant or duplicate corner points from the given list.
     *
     * @param points The list of corner points to check for redundancy.
     * @param availablePoints The list of available corner points to be cleaned.
     * @return The updated list of available corner points after removal of redundancies.
     */
    public ArrayList<CornerPoint> collect(ArrayList<CornerPoint> points, ArrayList<CornerPoint> availablePoints) {

        for (int j = 0; j < points.size(); j++) {
            for (int i = 0; i < availablePoints.size(); i++) {
                if ((points.get(j).getCoordinates().getX() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() + points.get(j).getBox().getDimensions().getLength() == availablePoints.get(i).getCoordinates().getZ())) {
                    availablePoints.remove(i);
                    System.out.println("Removed corner point. Condition 1");
                }
                if ((points.get(j).getCoordinates().getX() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() + points.get(j).getBox().getDimensions().getHeight() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() == availablePoints.get(i).getCoordinates().getZ())) {
                    System.out.println("Removed corner point. Condition 2");
                    availablePoints.remove(i);
                }
                if ((points.get(j).getCoordinates().getX() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() + points.get(j).getBox().getDimensions().getHeight() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() + points.get(j).getBox().getDimensions().getLength() == availablePoints.get(i).getCoordinates().getZ())) {
                    System.out.println("Removed corner point. Condition 3");
                    availablePoints.remove(i);
                }
                if ((points.get(j).getCoordinates().getX() + points.get(j).getBox().getDimensions().getWidth() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() == availablePoints.get(i).getCoordinates().getZ())) {
                    System.out.println("Removed corner point. Condition 4");
                    availablePoints.remove(i);
                }
                if ((points.get(j).getCoordinates().getX() + points.get(j).getBox().getDimensions().getWidth() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() + points.get(j).getBox().getDimensions().getLength() == availablePoints.get(i).getCoordinates().getZ())) {
                    System.out.println("Removed corner point. Condition 5");
                    availablePoints.remove(i);
                }
                if ((points.get(j).getCoordinates().getX() + points.get(j).getBox().getDimensions().getWidth() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() + points.get(j).getBox().getDimensions().getHeight() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() == availablePoints.get(i).getCoordinates().getZ())) {
                    System.out.println("Removed corner point. Condition 6");
                    availablePoints.remove(i);
                }
                if ((points.get(j).getCoordinates().getX() + points.get(j).getBox().getDimensions().getWidth() == availablePoints.get(i).getCoordinates().getX())
                        && (points.get(j).getCoordinates().getY() + points.get(j).getBox().getDimensions().getHeight() == availablePoints.get(i).getCoordinates().getY())
                        && (points.get(j).getCoordinates().getZ() + points.get(j).getBox().getDimensions().getLength() == availablePoints.get(i).getCoordinates().getZ())) {
                    System.out.println("Removed corner point. Condition 7");
                    availablePoints.remove(i);
                }
            }
        }

        for (int i = 0; i < availablePoints.size(); i++) {
            CornerPoint pci, pcj;
            pci = availablePoints.get(i);

            for (int j = i; j < availablePoints.size(); j++) {
                pcj = availablePoints.get(j);
                if (i != j) {
                    if (pci.getCoordinates().getX() == pcj.getCoordinates().getX()
                            && pci.getCoordinates().getY() == pcj.getCoordinates().getY()
                            && pci.getCoordinates().getZ() == pcj.getCoordinates().getZ()) {
                        availablePoints.remove(pcj);
                        System.out.println("Removed duplicate corner point");
                    }
                }
            }
        }

        return availablePoints;
    }
}

