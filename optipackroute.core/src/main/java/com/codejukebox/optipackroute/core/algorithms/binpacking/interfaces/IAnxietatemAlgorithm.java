package com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces;

import java.util.List;

import com.codejukebox.optipackroute.domain.models.binpacking.AnxietatemResultDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;

public interface IAnxietatemAlgorithm {
    void loadAvailableBoxes(List<Box> boxes);
    AnxietatemResultDTO pack();
    void displayPackedBoxes();
}
