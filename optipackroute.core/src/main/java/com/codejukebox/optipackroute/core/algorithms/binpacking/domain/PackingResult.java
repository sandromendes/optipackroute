package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

import java.util.List;

public class PackingResult {
    private List<PackedBox> packedBoxes;
    private int containerLength;
    private int containerHeight;
    private int containerDepth;

    public PackingResult(List<PackedBox> packedBoxes, int containerLength, int containerHeight, int containerDepth) {
        this.packedBoxes = packedBoxes;
        this.containerLength = containerLength;
        this.containerHeight = containerHeight;
        this.containerDepth = containerDepth;
    }

    public void printPacking() {
        System.out.println("Container dimensions: " + containerLength + "x" + containerHeight + "x" + containerDepth);
        System.out.println("Packed boxes:");
        for (PackedBox packedBox : packedBoxes) {
            System.out.println(packedBox);
        }
    }

	public List<PackedBox> getPackedBoxes() {
		return packedBoxes;
	}

	public void setPackedBoxes(List<PackedBox> packedBoxes) {
		this.packedBoxes = packedBoxes;
	}

	public int getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(int containerLength) {
		this.containerLength = containerLength;
	}

	public int getContainerHeight() {
		return containerHeight;
	}

	public void setContainerHeight(int containerHeight) {
		this.containerHeight = containerHeight;
	}

	public int getContainerDepth() {
		return containerDepth;
	}

	public void setContainerDepth(int containerDepth) {
		this.containerDepth = containerDepth;
	}
}

