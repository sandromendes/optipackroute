package com.codejukebox.optipackroute.domain.models.binpacking;

import java.util.List;

public class ContainerDTO {
    
	private int length;
    private int height;
    private int depth;
    private List<BoxDTO> boxes;
	
    public ContainerDTO() {
		super();
	}

	public ContainerDTO(int length, int height, int depth, List<BoxDTO> boxes) {
		super();
		this.length = length;
		this.height = height;
		this.depth = depth;
		this.boxes = boxes;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<BoxDTO> getBoxes() {
		return boxes;
	}

	public void setBoxes(List<BoxDTO> boxes) {
		this.boxes = boxes;
	}
}
