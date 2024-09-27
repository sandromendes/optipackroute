package com.codejukebox.optipackroute.domain.models.binpacking;

public class PackedBoxDTO {
    private String label;
    private int x;
    private int y;
    private int z;
    
	public PackedBoxDTO() {
		super();
	}

	public PackedBoxDTO(String label, int x, int y, int z) {
		super();
		this.label = label;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}    
}

