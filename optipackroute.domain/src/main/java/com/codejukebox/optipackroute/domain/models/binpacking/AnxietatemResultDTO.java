package com.codejukebox.optipackroute.domain.models.binpacking;

import java.util.List;

public class AnxietatemResultDTO {
	private List<CornerPoint> usedCornerPoints;
	private int numberOfPackedBoxes;
    private int containerLength;
    private int containerHeight;
    private int containerWidth;
    
    public AnxietatemResultDTO() {
		super();
	}

	public AnxietatemResultDTO(List<CornerPoint> usedCornerPoints, int numberOfPackedBoxes,
			int containerLength, int containerHeight, int containerWidth) {
		super();
		this.usedCornerPoints = usedCornerPoints;
		this.numberOfPackedBoxes = numberOfPackedBoxes;
		this.containerLength = containerLength;
		this.containerHeight = containerHeight;
		this.containerWidth = containerWidth;
	}

	public List<CornerPoint> getUsedCornerPoints() {
		return usedCornerPoints;
	}

	public void setUsedCornerPoints(List<CornerPoint> usedCornerPoints) {
		this.usedCornerPoints = usedCornerPoints;
	}
	
	public int getNumberOfPackedBoxes() {
		return numberOfPackedBoxes;
	}

	public void setNumberOfPackedBoxes(int numberOfPackedBoxes) {
		this.numberOfPackedBoxes = numberOfPackedBoxes;
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

	public int getContainerWidth() {
		return containerWidth;
	}

	public void setContainerWidth(int containerWidth) {
		this.containerWidth = containerWidth;
	}
}
