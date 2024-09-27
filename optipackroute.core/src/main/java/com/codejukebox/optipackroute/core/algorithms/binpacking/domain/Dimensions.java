package com.codejukebox.optipackroute.core.algorithms.binpacking.domain;

public class Dimensions {

    private int width, length, height;

    public Dimensions(int width, int length, int height) {
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}


