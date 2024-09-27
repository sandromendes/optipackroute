package com.codejukebox.optipackroute.core.algorithms.binpacking.guillotinesplit;

import java.util.ArrayList;
import java.util.List;

import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Box;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.PackedBox;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.PackingResult;
import com.codejukebox.optipackroute.core.algorithms.binpacking.domain.Space;
import com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces.IBinPackingAlgorithm;

public class GuillotineSplitPacker implements IBinPackingAlgorithm {

    private List<PackedBox> packedBoxes;
    private List<Box> availableBoxes;
    private List<Space> availableSpaces;

    private int containerLength, containerHeight, containerDepth;

    public GuillotineSplitPacker(int containerLength, int containerHeight, int containerDepth) {
        this.containerLength = containerLength;
        this.containerHeight = containerHeight;
        this.containerDepth = containerDepth;

        this.packedBoxes = new ArrayList<>();
        this.availableSpaces = new ArrayList<>();
        this.availableBoxes = new ArrayList<>();

        // Espaço inicial é o contêiner inteiro
        this.availableSpaces.add(new Space(0, 0, 0, containerLength, containerHeight, containerDepth));
    }

    @Override
    public void loadAvailableBoxes(List<Box> boxes) {
        this.availableBoxes = boxes;
    }

    @Override
    public PackingResult pack() {
        // Ordenar caixas pelo volume decrescente (First-Fit Decreasing)
        availableBoxes.sort((a, b) -> Integer.compare(b.getVolume(), a.getVolume()));

        // Empacotar cada caixa
        for (Box box : availableBoxes) {
            boolean packed = false;

            // Tentar empacotar a caixa em um dos espaços disponíveis
            for (Space space : new ArrayList<>(availableSpaces)) {
                if (fitsInSpace(box, space)) {
                    placeBox(box, space);
                    splitSpace(space, box);
                    packed = true;
                    break;
                }
            }

            if (!packed) {
                System.out.println("No space left for box: " + box);
                break;
            }
        }

        // Criar resultado do empacotamento
        return new PackingResult(packedBoxes, containerLength, containerHeight, containerDepth);
    }
    
    @Override
    public void displayPackedBoxes() {
        for (PackedBox box : packedBoxes) {
            System.out.println("Packed Box: " + box);
        }
    }

    private boolean fitsInSpace(Box box, Space space) {
        return box.getDimensions().getLength() <= space.getLength() &&
               box.getDimensions().getHeight() <= space.getHeight() &&
               box.getDimensions().getWidth() <= space.getDepth();
    }

    private void placeBox(Box box, Space space) {
        // Criar um objeto PackedBox contendo a posição e dimensões da caixa
        PackedBox packedBox = new PackedBox(box, space.getX(), space.getY(), space.getZ());
        packedBoxes.add(packedBox);
        System.out.println("Packed " + box + " at position (" + space.getX() + ", " + space.getY() + ", " + space.getZ() + ")");
    }

    // Método que realiza o corte guilhotina
    private void splitSpace(Space space, Box box) {
        int remainingLength = space.getLength() - box.getDimensions().getLength();
        int remainingHeight = space.getHeight() - box.getDimensions().getHeight();
        int remainingDepth = space.getDepth() - box.getDimensions().getWidth();

        // Remover o espaço que foi ocupado pela caixa
        availableSpaces.remove(space);

        // Dividir o espaço restante em duas novas regiões (guillotine split)
        if (remainingLength > 0) {
            availableSpaces.add(new Space(
                space.getX() + box.getDimensions().getLength(),
                space.getY(),
                space.getZ(),
                remainingLength,
                space.getHeight(),
                space.getDepth()
            ));
        }

        if (remainingHeight > 0) {
            availableSpaces.add(new Space(
                space.getX(),
                space.getY() + box.getDimensions().getHeight(),
                space.getZ(),
                space.getLength(),
                remainingHeight,
                space.getDepth()
            ));
        }

        if (remainingDepth > 0) {
            availableSpaces.add(new Space(
                space.getX(),
                space.getY(),
                space.getZ() + box.getDimensions().getWidth(),
                space.getLength(),
                space.getHeight(),
                remainingDepth
            ));
        }
    }
}


