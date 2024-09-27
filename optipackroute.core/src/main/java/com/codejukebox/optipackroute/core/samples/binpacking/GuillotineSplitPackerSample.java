package com.codejukebox.optipackroute.core.samples.binpacking;

import java.util.Arrays;

import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Box;
import com.codejukebox.optipackroute.core.algorithms.binpacking.guillotinesplit.GuillotineSplitPacker;

public class GuillotineSplitPackerSample {
    public static void main(String[] args) {
        // Dimensões do contêiner
        int containerLength = 10;
        int containerHeight = 10;
        int containerDepth = 10;

        // Criar o empacotador GuillotinePacker
        GuillotineSplitPacker packer = new GuillotineSplitPacker(containerLength, containerHeight, containerDepth);

        // Criar algumas caixas com dimensões diferentes
        Box box1 = new Box("Box1", 5, 5, 5);
        Box box2 = new Box("Box2", 3, 3, 3);
        Box box3 = new Box("Box3", 7, 4, 2);
        Box box4 = new Box("Box4", 2, 2, 2);

        // Adicionar as caixas para empacotamento
        packer.loadAvailableBoxes(Arrays.asList(box1, box2, box3, box4));

        // Empacotar as caixas
        packer.pack();
    }
}
