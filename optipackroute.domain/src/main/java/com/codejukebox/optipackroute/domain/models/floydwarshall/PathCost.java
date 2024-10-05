package com.codejukebox.optipackroute.domain.models.floydwarshall;

public class PathCost {

	private Integer origin;
	private Integer destiny;
	private Double subCost;

	public PathCost() {
		super();
	}

	public PathCost(Integer origin, Integer destiny, Double subCost) {
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

	public Double getSubCost() {
		return subCost;
	}

	public void setSubCost(Double subCost) {
		this.subCost = subCost;
	}
}
