package com.codejukebox.optipackroute.core.algorithms.binpacking.bottomleftback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Box;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Coordinates;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.CornerPoint;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.PackedBox;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.PackingResult;
import com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces.IBinPackingAlgorithm;

public class BottomLeftBackPacker implements IBinPackingAlgorithm {
    private List<CornerPoint> availableCornerPoints;
    private List<PackedBox> packedBoxes;
    private List<Box> availableBoxes;
    private int containerLength;
    private int containerHeight;
    private int containerDepth;

    public BottomLeftBackPacker(int containerLength, int containerHeight, int containerDepth) {
        this.containerLength = containerLength;
        this.containerHeight = containerHeight;
        this.containerDepth = containerDepth;
        this.packedBoxes = new ArrayList<>();
        this.availableCornerPoints = new ArrayList<>();
        this.availableBoxes = new ArrayList<>();
    }

    @Override
    public void loadAvailableBoxes(List<Box> boxes) {
        this.availableBoxes = new ArrayList<>(boxes);
    }

    @Override
    public PackingResult pack() {
        // Inicializa o canto inicial do container (0, 0, 0)
        availableCornerPoints.add(new CornerPoint(new Coordinates(0, 0, 0)));

        // Loop principal para tentar empacotar todas as caixas
        while (!availableBoxes.isEmpty()) {
            Box box = selectBox();
            if (box == null) break; // Se não houver caixa para empacotar, sair do loop

            CornerPoint bestCornerPoint = findBestCornerPoint(box);

            if (bestCornerPoint != null) {
                Coordinates position = bestCornerPoint.getCoordinates();
                PackedBox packedBox = new PackedBox(box, position.getX(), position.getY(), position.getZ());
                packedBoxes.add(packedBox);
                availableBoxes.remove(box);
                generateNewCornerPoints(bestCornerPoint, box);
                System.out.println("Packed " + box + " at " + bestCornerPoint);
            } else {
                System.out.println("No available space for " + box);
                break;
            }
        }

        System.out.println("Total packed boxes: " + packedBoxes.size());
        return new PackingResult(packedBoxes, containerLength, containerHeight, containerDepth);
    }

    @Override
    public void displayPackedBoxes() {
        for (PackedBox box : packedBoxes) {
            System.out.println("Packed Box: " + box);
        }
    }
    
    private CornerPoint findBestCornerPoint(Box box) {
        return availableCornerPoints.stream()
                .filter(cp -> fitsInContainer(box, cp))
                .min(Comparator.comparingInt((CornerPoint cp) -> cp.getCoordinates().getY()) // Minimize Y (bottom)
                        .thenComparingInt(cp -> cp.getCoordinates().getX()) // Then minimize X (left)
                        .thenComparingInt(cp -> cp.getCoordinates().getZ())) // Then minimize Z (back)
                .orElse(null); // Retorna null se não encontrar espaço
    }

    private boolean fitsInContainer(Box box, CornerPoint cornerPoint) {
        Coordinates coordinates = cornerPoint.getCoordinates();
        return (coordinates.getX() + box.getDimensions().getLength() <= containerLength) &&
               (coordinates.getY() + box.getDimensions().getHeight() <= containerHeight) &&
               (coordinates.getZ() + box.getDimensions().getWidth() <= containerDepth);
    }

    private Box selectBox() {
        // Seleciona a caixa de maior volume primeiro (First-Fit Decreasing)
        return availableBoxes.stream()
                .max(Comparator.comparingInt(box -> box.getDimensions().getLength() *
                                                    box.getDimensions().getHeight() *
                                                    box.getDimensions().getWidth()))
                .orElse(null);
    }

    private void generateNewCornerPoints(CornerPoint cornerPoint, Box box) {
        Coordinates coordinates = cornerPoint.getCoordinates();

        // Gera novos pontos de canto com base nas dimensões da caixa embalada
        List<Coordinates> newPoints = new ArrayList<>();
        newPoints.add(new Coordinates(coordinates.getX() + box.getDimensions().getLength(), coordinates.getY(), coordinates.getZ()));
        newPoints.add(new Coordinates(coordinates.getX(), coordinates.getY() + box.getDimensions().getHeight(), coordinates.getZ()));
        newPoints.add(new Coordinates(coordinates.getX(), coordinates.getY(), coordinates.getZ() + box.getDimensions().getWidth()));

        // Adiciona os novos pontos de canto à lista
        for (Coordinates point : newPoints) {
            if (isValidPoint(point)) {
                availableCornerPoints.add(new CornerPoint(point));
            }
        }
    }

    private boolean isValidPoint(Coordinates point) {
        // Verifica se o ponto está dentro dos limites do contêiner
        return point.getX() >= 0 && point.getY() >= 0 && point.getZ() >= 0 &&
               point.getX() <= containerLength && point.getY() <= containerHeight && point.getZ() <= containerDepth;
    }
}


