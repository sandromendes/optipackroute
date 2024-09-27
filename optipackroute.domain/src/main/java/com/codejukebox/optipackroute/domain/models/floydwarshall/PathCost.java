package com.codejukebox.optipackroute.domain.models.floydwarshall;

public class PathCost {

	private Integer origin;
	private Integer destiny;
	private Integer subCost;
	
	public PathCost() {
		super();
	}
	
	public PathCost(Integer origin, Integer destiny, Integer subCost) {
		super();
		this.origin = origin;
		this.destiny = destiny;
		this.subCost = subCost;
	}

	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public Integer getDestiny() {
		return destiny;
	}
	public void setDestiny(Integer destiny) {
		this.destiny = destiny;
	}
	public Integer getSubCost() {
		return subCost;
	}
	public void setSubCost(Integer subCost) {
		this.subCost = subCost;
	}
}
