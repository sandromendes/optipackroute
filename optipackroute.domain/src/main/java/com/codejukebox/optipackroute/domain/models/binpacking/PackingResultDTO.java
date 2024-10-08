package com.codejukebox.optipackroute.domain.models.binpacking;

import java.util.List;

public class PackingResultDTO {
    
	private List<PackedBoxDTO> packedBoxes;
	private int numberOfPackedBoxes;
    private int containerLength;
    private int containerHeight;
    private int containerWidth;
    
    public PackingResultDTO() {
		super();
	}

	public PackingResultDTO(List<PackedBoxDTO> packedBoxes, int numberOfPackedBoxes,
			int containerLength, int containerHeight, int containerWidth) {
		super();
		this.packedBoxes = packedBoxes;
		this.numberOfPackedBoxes = numberOfPackedBoxes;
		this.containerLength = containerLength;
		this.containerHeight = containerHeight;
		this.containerWidth = containerWidth;
	}

	public List<PackedBoxDTO> getPackedBoxes() {
		return packedBoxes;
	}

	public void setPackedBoxes(List<PackedBoxDTO> packedBoxes) {
		this.packedBoxes = packedBoxes;
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

