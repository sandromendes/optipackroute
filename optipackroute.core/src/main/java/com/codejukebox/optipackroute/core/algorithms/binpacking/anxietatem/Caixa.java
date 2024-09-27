package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

public class Caixa {

    private int ID;

    private Dimensoes dimensoes;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Dimensoes getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(Dimensoes dimensoes) {
        this.dimensoes = dimensoes;
    }
    
    @Override
    public String toString() {
        return "Largura: " +Integer.toString(dimensoes.getLargura())
        	+ ", Altura: " +Integer.toString(dimensoes.getAltura())
        	+ ", Profundidade: " + Integer.toString(dimensoes.getComprimento());
    }
}
