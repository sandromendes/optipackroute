package com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces;

import java.util.List;

import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResult;

public interface IBinPackingAlgorithm {
    void loadAvailableBoxes(List<Box> boxes);
    PackingResult pack();
    void displayPackedBoxes();
}

