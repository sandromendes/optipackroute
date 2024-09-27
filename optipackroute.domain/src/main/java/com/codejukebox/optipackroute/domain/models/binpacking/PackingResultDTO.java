package com.codejukebox.optipackroute.domain.models.binpacking;

import java.util.List;

public class PackingResultDTO {
    
	private List<PackedBoxDTO> packedBoxes;
    private int containerLength;
    private int containerHeight;
    private int containerDepth;
    
    public PackingResultDTO() {
		super();
	}

	public PackingResultDTO(List<PackedBoxDTO> packedBoxes, int containerLength, int containerHeight,
			int containerDepth) {
		super();
		this.packedBoxes = packedBoxes;
		this.containerLength = containerLength;
		this.containerHeight = containerHeight;
		this.containerDepth = containerDepth;
	}

	public List<PackedBoxDTO> getPackedBoxes() {
		return packedBoxes;
	}

	public void setPackedBoxes(List<PackedBoxDTO> packedBoxes) {
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

