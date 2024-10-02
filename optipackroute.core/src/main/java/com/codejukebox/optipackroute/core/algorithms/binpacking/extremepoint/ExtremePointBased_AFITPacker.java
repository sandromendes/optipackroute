package com.codejukebox.optipackroute.core.algorithms.binpacking.extremepoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces.IBinPackingAlgorithm;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.PackedBox;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResult;
import com.codejukebox.optipackroute.domain.models.binpacking.Space;

public class ExtremePointBased_AFITPacker implements IBinPackingAlgorithm {
    private int containerLength, containerHeight, containerDepth;
    private List<PackedBox> packedBoxes;
    private List<Box> availableBoxes;
    private List<Space> extremePoints;

    public ExtremePointBased_AFITPacker(int containerLength, int containerHeight, int containerDepth) {
        this.containerLength = containerLength;
        this.containerHeight = containerHeight;
        this.containerDepth = containerDepth;
        this.packedBoxes = new ArrayList<>();
        this.extremePoints = new ArrayList<>();
        this.extremePoints.add(new Space(0, 0, 0, containerLength, containerHeight, containerDepth));  // Começa no ponto (0,0,0)
    }

    @Override
    public void loadAvailableBoxes(List<Box> boxes) {
    	this.availableBoxes = boxes;
    }

    @Override
    public PackingResult pack() {
    	availableBoxes.sort(Comparator.comparing(Box::getVolume).reversed());  // Ordena caixas por volume decrescente

        for (var box : availableBoxes) {
            boolean packed = false;
            for (var extremePoint : new ArrayList<>(extremePoints)) {
                if (canFit(box, extremePoint)) {
                    placeBox(box, extremePoint);
                    packed = true;
                    break;
                }
            }
            if (!packed) {
                System.out.println("Could not pack box: " + box);
            }
        }

        // Criar resultado do empacotamento
        return new PackingResult(packedBoxes, containerLength, containerHeight, containerDepth);
    }

    private boolean canFit(Box box, Space space) {
        return box.getDimensions().getLength() <= space.getLength()
                && box.getDimensions().getHeight() <= space.getHeight()
                && box.getDimensions().getWidth() <= space.getDepth();
    }

    private void placeBox(Box box, Space extremePoint) {
        var packedBox = new PackedBox(box, extremePoint.getX(), extremePoint.getY(), extremePoint.getZ());
        packedBoxes.add(packedBox);
        System.out.println("Packed " + box + " at " + extremePoint);

        // Atualizar pontos extremos (gerar novos pontos após empacotamento)
        updateExtremePoints(box, extremePoint);
    }

    private void updateExtremePoints(Box box, Space space) {
        // Remover o ponto extremo atual
        extremePoints.remove(space);

        int boxLength = box.getDimensions().getLength();
        int boxHeight = box.getDimensions().getHeight();
        int boxDepth = box.getDimensions().getWidth();

        // Criar novos pontos extremos nos cantos da caixa empacotada
        if (space.getX() + boxLength < containerLength) {
            extremePoints.add(new Space(space.getX() + boxLength, space.getY(), space.getZ(),
                    containerLength - (space.getX() + boxLength), space.getHeight(), space.getDepth()));
        }

        if (space.getY() + boxHeight < containerHeight) {
            extremePoints.add(new Space(space.getX(), space.getY() + boxHeight, space.getZ(),
                    containerLength, containerHeight - (space.getY() + boxHeight), space.getDepth()));
        }

        if (space.getZ() + boxDepth < containerDepth) {
            extremePoints.add(new Space(space.getX(), space.getY(), space.getZ() + boxDepth,
                    containerLength, containerHeight, containerDepth - (space.getZ() + boxDepth)));
        }
    }

    @Override
    public void displayPackedBoxes() {
        for (var packedBox : packedBoxes) {
            System.out.println("Packed Box: " + packedBox.getBox() + " at position (" +
                packedBox.getX() + ", " + packedBox.getY() + ", " + packedBox.getZ() + ")");
        }
    }
}


