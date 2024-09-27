package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Box;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Coordinates;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.CornerPoint;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Dimensions;

/**
 * The Packer class manages the packing of boxes into a container, 
 * considering the selection of boxes and corner points, and generates new 
 * corner points based on the packed boxes.
 */
public class AnxietatemAlgorithm {

    private int state = 1; // Current state of the packer
    private ArrayList<CornerPoint> availableCornerPoints; // Set of available corner points
    private ArrayList<CornerPoint> usedCornerPoints; // Set of points already used and in sequence
    private ArrayList<Box> packedBoxes; // Set of packed boxes
    private ArrayList<Box> unpackedBoxes; // Set of available boxes
    private Box selectedBox; // Currently selected box

    /**
     * Constructor for the Packer class.
     */
    public AnxietatemAlgorithm() {
    }

    /**
     * Randomly selects a box from the set of unpacked boxes and 
     * adds it to the set of packed boxes.
     * 
     * @return The randomly selected box.
     * 
     * @example
     * <pre>
     * Packer packer = new Packer();
     * Box box = packer.chooseBox();
     * System.out.println("Chosen box: " + box.getID());
     * </pre>
     */
    public Box chooseBox() {

        Box box = null;
        Random random = new Random();

        int randomIndex = random.nextInt(unpackedBoxes.size());
        box = unpackedBoxes.get(randomIndex);

        packedBoxes.add(box);
        unpackedBoxes.remove(randomIndex);
        System.out.println("Box " + box.getID() + " chosen");

        return box;

    }

    /**
     * Randomly selects a corner point from the set of available corner points and
     * adds it to the set of used corner points.
     * 
     * @return The randomly selected corner point.
     * 
     * @example
     * <pre>
     * Packer packer = new Packer();
     * CornerPoint point = packer.choosePoint();
     * System.out.println("Chosen corner point: " + point.getCornerPoint());
     * </pre>
     */
    public CornerPoint choosePoint() {
        CornerPoint cornerPoint = null;

        //Random Heuristic
        Random random = new Random();
        int randomIndex = random.nextInt(availableCornerPoints.size());

        System.out.println("Chosen random number: " + randomIndex);

        cornerPoint = availableCornerPoints.get(randomIndex);
        usedCornerPoints.add(cornerPoint);
        availableCornerPoints.remove(randomIndex);

        /*
         * Left heuristic:
         * Pack at the leftmost and lowest corner point
         */

        return cornerPoint;
    }

    /**
     * Fills the set of unpacked boxes with default boxes 
     * up to the specified limit.
     * 
     * @param limit The number of boxes to be added.
     * 
     * @example
     * <pre>
     * Packer packer = new Packer();
     * packer.loadBoxArray(50);
     * </pre>
     */
    public void loadBoxArray(int limit) {

        unpackedBoxes = new ArrayList<Box>();

        for (int i = 0; i < limit; i++) {
            Box box = new Box();
            box.setID(i);
            Dimensions dimensions = new Dimensions(8, 5, 4);
            box.setDimensions(dimensions);
            unpackedBoxes.add(box);
        }
    }

    /**
     * Fills the set of unpacked boxes with randomly generated boxes.
     * 
     * @example
     * <pre>
     * Packer packer = new Packer();
     * packer.loadRandomBoxArray();
     * </pre>
     */
    public void loadRandomBoxArray() {

        unpackedBoxes = new ArrayList<Box>();
        Random random = new Random();
        int width, length, height;
        for (int i = 0; i < 200; i++) {
            Box box = new Box();
            box.setID(i);
            width = random.nextInt(10) + 1;
            length = random.nextInt(10) + 1;
            height = random.nextInt(10) + 1;
            Dimensions dimensions = new Dimensions(width, length, height);
            box.setDimensions(dimensions);
            unpackedBoxes.add(box);
        }
    }

    /**
     * Packs boxes into a container using an initial corner point.
     * The process considers the random selection of boxes and corner points,
     * and avoids exceeding the container's limits.
     * 
     * @param container The container where the boxes will be packed.
     * @param cornerPoint The initial corner point for packing.
     * @param numberOfBoxes The number of boxes to be loaded.
     * 
     * @example
     * <pre>
     * Packer packer = new Packer();
     * Box container = new Box();
     * CornerPoint initialPoint = new CornerPoint(new Coordinates(0, 0, 0));
     * packer.pack(container, initialPoint, 100);
     * </pre>
     */
    public List<CornerPoint> pack(Box container, CornerPoint cornerPoint, int numberOfBoxes) {

        loadRandomBoxArray();

        availableCornerPoints = new ArrayList<CornerPoint>();
        usedCornerPoints = new ArrayList<CornerPoint>();

        packedBoxes = new ArrayList<Box>();

        usedCornerPoints.add(cornerPoint);

        availableCornerPoints.add(cornerPoint);

        int width, height, length;

        width = container.getDimensions().getWidth();
        height = container.getDimensions().getHeight();
        length = container.getDimensions().getLength();

        /*
         * Stopping condition:
         * When no more boxes can be packed, that is,
         * when any box to be packed exceeds the container's limit
         */

        System.out.println("Box array size before Loop: " + unpackedBoxes.size());
        for (int i = 0; unpackedBoxes.size() > 0; i++) {
            System.out.println("Box array size is: " + availableCornerPoints.size() + " in iteration number: " + i);

            if (state == 1) {
                selectedBox = chooseBox();
                cornerPoint = choosePoint();
                System.out.println("Corner with coordinates: " + cornerPoint.getCoordinates().getX() + "," + cornerPoint.getCoordinates().getY() + "," + cornerPoint.getCoordinates().getZ() + " CHOSEN. Condition st = 1");
            } else if (state == 0) {
                selectedBox = chooseBox();
                System.out.println("Corner with coordinates: " + cornerPoint.getCoordinates().getX() + "," + cornerPoint.getCoordinates().getY() + "," + cornerPoint.getCoordinates().getZ() + " CHOSEN. Condition st = 0");
            }

            Coordinates cornerCoordinates = new Coordinates(cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ());
            Coordinates boxXCoordinate = new Coordinates(width, 0, 0);
            Coordinates boxYCoordinate = new Coordinates(0, height, 0);
            Coordinates boxZCoordinate = new Coordinates(0, 0, length);

            if ((calculateDistanceBetweenPoints(cornerCoordinates, boxXCoordinate) > selectedBox.getDimensions().getWidth())
                    && (calculateDistanceBetweenPoints(cornerCoordinates, boxYCoordinate) > selectedBox.getDimensions().getHeight())
                    && (calculateDistanceBetweenPoints(cornerCoordinates, boxZCoordinate) > selectedBox.getDimensions().getLength())) {
                cornerPoint.setBox(selectedBox);
                generateCornerPoint(cornerPoint, selectedBox);
                state = 1;
            } else {

                state = 0;
            }

             Runtime runtime = Runtime.getRuntime();
             runtime.gc();
        }

        System.out.println("Number of used points: " + usedCornerPoints.size());
        System.out.println("Number of packed boxes: " + packedBoxes.size());
        
        return usedCornerPoints; // Return the HashMap with corner points and boxes
    }

    /**
     * Generates new corner points based on the dimensions of the box and the provided coordinates.
     * 
     * @param cornerPoint The corner point to be used.
     * @param box The box that defines the dimensions for calculating the new corner points.
     */
    public void generateCornerPoint(CornerPoint cornerPoint, Box box) {
        // Choice of corner points
        // if pci = <0,0,0>

        Coordinates coordinate;
        ArrayList<CornerPoint> points = new ArrayList<CornerPoint>();

        if ((cornerPoint.getCoordinates().getX() == 0) && (cornerPoint.getCoordinates().getY() == 0) && (cornerPoint.getCoordinates().getZ() == 0)) {
            // Create new corner points

            coordinate = new Coordinates(box.getDimensions().getWidth(), 0, 0);
            CornerPoint pci1 = new CornerPoint(coordinate);
            pci1.setBox(box);
            
            System.out.println("Point with coordinates: " 
                    + pci1.getCoordinates().getX() + "," 
                    + pci1.getCoordinates().getY() + "," 
                    + pci1.getCoordinates().getZ() + " GENERATED");
            
            availableCornerPoints.add(pci1);
            points.add(pci1);
            
            coordinate = new Coordinates(0, box.getDimensions().getHeight(), 0);
            CornerPoint pci2 = new CornerPoint(coordinate);
            pci2.setBox(box);
            
            System.out.println("Point with coordinates: " 
                    + pci2.getCoordinates().getX() + "," 
                    + pci2.getCoordinates().getY() + "," 
                    + pci2.getCoordinates().getZ() + " GENERATED");
            
            availableCornerPoints.add(pci2);
            points.add(pci2);
            
            coordinate = new Coordinates(0, 0, box.getDimensions().getLength());
            CornerPoint pci3 = new CornerPoint(coordinate);
            pci3.setBox(box);
            
            System.out.println("Point with coordinates: " 
                    + pci3.getCoordinates().getX() + "," 
                    + pci3.getCoordinates().getY() + "," 
                    + pci3.getCoordinates().getZ() + " GENERATED");

            availableCornerPoints.add(pci3);
            points.add(pci3);
        } else {

            if (cornerPoint.getBox().getDimensions().getWidth() < box.getDimensions().getWidth()) {
                coordinate = new Coordinates(cornerPoint.getCoordinates().getX() + cornerPoint.getBox().getDimensions().getWidth(), cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ());
                
                CornerPoint generatedByWidth = new CornerPoint(coordinate);
                generatedByWidth.setBox(box);
                
                System.out.println("Point with coordinates: " + generatedByWidth.getCoordinates().getX() + "," + generatedByWidth.getCoordinates().getY() + "," + generatedByWidth.getCoordinates().getZ() + " GENERATED");
                
                availableCornerPoints.add(generatedByWidth);
                points.add(generatedByWidth);
            } else {
                coordinate = new Coordinates(cornerPoint.getCoordinates().getX() + box.getDimensions().getWidth(), cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ());
                
                CornerPoint generatedByWidth = new CornerPoint(coordinate);
                generatedByWidth.setBox(box);
                
                System.out.println("Point with coordinates: " + generatedByWidth.getCoordinates().getX() + "," + generatedByWidth.getCoordinates().getY() + "," + generatedByWidth.getCoordinates().getZ() + " GENERATED");
                
                availableCornerPoints.add(generatedByWidth);
                points.add(generatedByWidth);
            }

            if (cornerPoint.getBox().getDimensions().getLength() < box.getDimensions().getLength()) {
                coordinate = new Coordinates(cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ() + cornerPoint.getBox().getDimensions().getLength());
                
                CornerPoint generatedByLength = new CornerPoint(coordinate);
                generatedByLength.setBox(box);
                
                System.out.println("Point with coordinates: " + generatedByLength.getCoordinates().getX() + "," + generatedByLength.getCoordinates().getY() + "," + generatedByLength.getCoordinates().getZ() + " GENERATED");
                
                availableCornerPoints.add(generatedByLength);
                points.add(generatedByLength);
            } else {
                coordinate = new Coordinates(cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ() + box.getDimensions().getLength());
                
                CornerPoint generatedByLength = new CornerPoint(coordinate);
                generatedByLength.setBox(box);
                
                System.out.println("Point with coordinates: " + generatedByLength.getCoordinates().getX() + "," + generatedByLength.getCoordinates().getY() + "," + generatedByLength.getCoordinates().getZ() + " GENERATED");
                
                availableCornerPoints.add(generatedByLength);
                points.add(generatedByLength);
            }

            if (cornerPoint.getBox().getDimensions().getHeight() < box.getDimensions().getHeight()) {
                coordinate = new Coordinates(cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY() + cornerPoint.getBox().getDimensions().getHeight(), cornerPoint.getCoordinates().getZ());
                
                CornerPoint generatedByHeight = new CornerPoint(coordinate);
                generatedByHeight.setBox(box);
                
                System.out.println("Point with coordinates: " + generatedByHeight.getCoordinates().getX() + "," + generatedByHeight.getCoordinates().getY() + "," + generatedByHeight.getCoordinates().getZ() + " GENERATED");
                
                availableCornerPoints.add(generatedByHeight);
                points.add(generatedByHeight);
            } else {
                coordinate = new Coordinates(cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY() + box.getDimensions().getHeight(), cornerPoint.getCoordinates().getZ());
                CornerPoint generatedByHeight = new CornerPoint(coordinate);
                generatedByHeight.setBox(box);
                System.out.println("Point with coordinates: " + generatedByHeight.getCoordinates().getX() + "," + generatedByHeight.getCoordinates().getY() + "," + generatedByHeight.getCoordinates().getZ() + " GENERATED");
                availableCornerPoints.add(generatedByHeight);
                points.add(generatedByHeight);
            }
        }

        System.out.println("Size of available points array before garbage: " + availableCornerPoints.size());

        GarbagePoint garbagePoint = new GarbagePoint();
        availableCornerPoints = garbagePoint.collect(points, availableCornerPoints);

        System.out.println("Size of available points array after garbage: " + availableCornerPoints.size());
    }


    /**
     * Calculates the distance between two points in 3D space.
     * 
     * @param p1 The first point.
     * @param p2 The second point.
     * @return The Euclidean distance between the two points.
     */
    public double calculateDistanceBetweenPoints(Coordinates p1, Coordinates p2) {
        return Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()), 2) + Math.pow((p1.getZ() - p2.getZ()), 2));
    }

}
