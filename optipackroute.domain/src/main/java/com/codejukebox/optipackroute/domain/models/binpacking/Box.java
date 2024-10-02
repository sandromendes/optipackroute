package com.codejukebox.optipackroute.domain.models.binpacking;

public class Box {

    private int ID;

    private Dimensions dimensions;

    private String label; 
            
    public Box() {
		super();
	}
    
	public Box(String label, int x, int y, int z) {
		super();
		this.dimensions = new Dimensions(x, y, z);
		this.label = label;
	}

	public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }
    
    @Override
    public String toString() {
        return "Width: " + Integer.toString(dimensions.getWidth())
            + ", Height: " + Integer.toString(dimensions.getHeight())
            + ", Length: " + Integer.toString(dimensions.getLength());
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getVolume() {
		return dimensions.getLength()*dimensions.getWidth()*dimensions.getHeight();
	}
}

