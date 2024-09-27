package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

public class Dimensoes {

    private int larg, comp, alt;

    public Dimensoes(int larg, int comp, int alt) {
        this.larg = larg;
        this.comp = comp;
        this.alt = alt;
    }


    public int getAltura() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public int getComprimento() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public int getLargura() {
        return larg;
    }

    public void setLarg(int larg) {
        this.larg = larg;
    }

}
