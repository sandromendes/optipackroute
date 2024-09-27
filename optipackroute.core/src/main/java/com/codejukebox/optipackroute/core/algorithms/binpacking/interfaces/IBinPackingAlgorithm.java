package com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces;

import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Box;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.PackingResult;

public interface IBinPackingAlgorithm {
    void loadAvailableBoxes(List<Box> boxes);
    PackingResult pack();
    void displayPackedBoxes();
}

