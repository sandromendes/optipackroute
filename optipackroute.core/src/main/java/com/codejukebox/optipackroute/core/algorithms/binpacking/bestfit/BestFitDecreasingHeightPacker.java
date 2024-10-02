package com.codejukebox.optipackroute.core.algorithms.binpacking.bestfit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces.IBinPackingAlgorithm;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.PackedBox;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResult;
import com.codejukebox.optipackroute.domain.models.binpacking.Space;

public class BestFitDecreasingHeightPacker implements IBinPackingAlgorithm {
    private int containerLength, containerHeight, containerDepth;
    private List<PackedBox> packedBoxes;
    private List<Box> availableBoxes;
    private int currentHeightLevel;

    public BestFitDecreasingHeightPacker(int containerLength, int containerHeight, int containerDepth) {
        this.containerLength = containerLength;
        this.containerHeight = containerHeight;
        this.containerDepth = containerDepth;
        this.packedBoxes = new ArrayList<>();
        this.currentHeightLevel = 0;
    }

    @Override
    public void loadAvailableBoxes(List<Box> boxes) {
        this.availableBoxes = boxes;
    }

    @Override
    public PackingResult pack() {
        // Ordena caixas por altura de forma decrescente
        Collections.sort(availableBoxes, (box1, box2) -> Integer.compare(box2.getDimensions().getHeight(), box1.getDimensions().getHeight()));

        // Começa a empacotar em camadas
        var layers = new ArrayList<Space>();
        layers.add(new Space(0, 0, 0, containerLength, containerHeight, containerDepth));  // Primeira camada

        for (Box box : availableBoxes) {
            boolean packed = false;

            // Tentar empacotar a caixa na camada atual
            for (var layer : new ArrayList<Space>(layers)) {
                if (canFit(box, layer)) {
                    placeBox(box, layer);
                    packed = true;
                    break;
                }
            }

            // Se não couber na camada atual, iniciar uma nova camada
            if (!packed) {
                currentHeightLevel += box.getDimensions().getHeight();
                if (currentHeightLevel + box.getDimensions().getHeight() <= containerHeight) {
                    var newLayer = new Space(0, currentHeightLevel, 0, containerLength, containerHeight - currentHeightLevel, containerDepth);
                    layers.add(newLayer);
                    placeBox(box, newLayer);
                } else {
                    System.out.println("Not enough space to pack box: " + box);
                }
            }
        }

        // Criação do resultado de empacotamento usando as dimensões do container
        return new PackingResult(packedBoxes, containerLength, containerHeight, containerDepth);
    }

    private boolean canFit(Box box, Space space) {
        return box.getDimensions().getLength() <= space.getLength()
                && box.getDimensions().getHeight() <= space.getHeight()
                && box.getDimensions().getWidth()<= space.getDepth();
    }

    private void placeBox(Box box, Space space) {
        // Criar um objeto PackedBox contendo a posição e dimensões da caixa
        var packedBox = new PackedBox(box, space.getX(), space.getY(), space.getZ());
        packedBoxes.add(packedBox);
        System.out.println("Packed " + box + " at position (" + space.getX() + ", " + space.getY() + ", " + space.getZ() + ")");

        // Reduzir o espaço restante na camada
        space = new Space(space.getX() + box.getDimensions().getLength(), space.getY(), space.getZ(),
                space.getLength() - box.getDimensions().getLength(), space.getHeight(), space.getDepth());
    }
    
    // Método auxiliar para exibir as caixas empacotadas (pode ser útil para debugging)
    public void displayPackedBoxes() {
        for (var box : packedBoxes) {
            System.out.println("Packed Box: " + box);
        }
    }
}

