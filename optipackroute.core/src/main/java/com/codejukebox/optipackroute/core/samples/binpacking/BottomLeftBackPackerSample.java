package com.codejukebox.optipackroute.core.samples.binpacking;

import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.bottomleftback.BottomLeftBackPacker;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;

public class BottomLeftBackPackerSample {
    public static void main(String[] args) {
        int containerLength = 10;
        int containerHeight = 10;
        int containerDepth = 10;

        var box1 = new Box("Box1", 5, 5, 5);
        var box2 = new Box("Box2", 3, 3, 3);
        var box3 = new Box("Box3", 7, 4, 2);
        var box4 = new Box("Box4", 2, 2, 2);

        var packer = new BottomLeftBackPacker(containerLength, containerHeight, containerDepth);
        packer.loadAvailableBoxes(List.of(box1, box2, box3, box4));
        var result = packer.pack();
        result.printPacking();
    }
}
