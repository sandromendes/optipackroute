package com.codejukebox.optipackroute.domain.models.binpacking;

public class BoxDTO {
    private int length;
    private int height;
    private int depth;
    private String label;
      
	public BoxDTO() {
		super();
	}

	public BoxDTO(int length, int height, int depth, String label) {
		super();
		this.length = length;
		this.height = height;
		this.depth = depth;
		this.label = label;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}	
}

