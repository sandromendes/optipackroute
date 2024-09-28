package com.codejukebox.optipackroute.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.core.algorithms.binpacking.bestfit.BestFitDecreasingHeightPacker;
import com.codejukebox.optipackroute.core.algorithms.binpacking.bottomleftback.BottomLeftBackPacker;
import com.codejukebox.optipackroute.core.algorithms.binpacking.extremepoint.ExtremePointBased_AFITPacker;
import com.codejukebox.optipackroute.core.algorithms.binpacking.guillotinesplit.GuillotineSplitPacker;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.BoxDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.ContainerDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.PackedBoxDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResult;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResultDTO;

@Service
public class BinPackingService {

    public PackingResultDTO packUsingBottomLeftBack(ContainerDTO containerDTO) {
        var packer = new BottomLeftBackPacker(
                containerDTO.getLength(),
                containerDTO.getHeight(),
                containerDTO.getDepth()
        );
        var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
        packer.loadAvailableBoxes(boxes);
        var result = packer.pack();
        return mapPackingResultToDTO(result);
    }

    public PackingResultDTO packUsingBestFitDecreasingHeight(ContainerDTO containerDTO) {
        var packer = new BestFitDecreasingHeightPacker(
                containerDTO.getLength(),
                containerDTO.getHeight(),
                containerDTO.getDepth()
        );
        var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
        packer.loadAvailableBoxes(boxes);
        var result = packer.pack();
        return mapPackingResultToDTO(result);
    }

    public PackingResultDTO packUsingGuillotineSplit(ContainerDTO containerDTO) {
        var packer = new GuillotineSplitPacker(
                containerDTO.getLength(),
                containerDTO.getHeight(),
                containerDTO.getDepth()
        );
        var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
        packer.loadAvailableBoxes(boxes);
        var result = packer.pack();
        return mapPackingResultToDTO(result);
    }

    public PackingResultDTO packUsingExtremePointBasedAFIT(ContainerDTO containerDTO) {
        var packer = new ExtremePointBased_AFITPacker(
                containerDTO.getLength(),
                containerDTO.getHeight(),
                containerDTO.getDepth()
        );
        var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
        packer.loadAvailableBoxes(boxes);
        var result = packer.pack();
        return mapPackingResultToDTO(result);
    }

    // Mapeamento das entidades de domínio para DTO e vice-versa
    private List<Box> mapBoxDTOToBox(List<BoxDTO> boxDTOList) {
        // Mapeia os BoxDTO para Box (Domínio)
        var boxes = new ArrayList<Box>();
        for (BoxDTO dto : boxDTOList) {
            boxes.add(new Box(dto.getLabel(), dto.getHeight(), dto.getDepth(), dto.getLength()));
        }
        return boxes;
    }

    private PackingResultDTO mapPackingResultToDTO(PackingResult result) {
        // Mapeia o resultado do empacotamento para PackingResultDTO
        var resultDTO = new PackingResultDTO();
        resultDTO.setContainerLength(result.  getContainerLength());
        resultDTO.setContainerHeight(result.getContainerHeight());
        resultDTO.setContainerDepth(result.getContainerDepth());

        var packedBoxDTOs = new ArrayList<PackedBoxDTO>();
        for (var packedBox : result.getPackedBoxes()) {
            var packedBoxDTO = new PackedBoxDTO();
            packedBoxDTO.setLabel(packedBox.getBox().getLabel());
            packedBoxDTO.setX(packedBox.getX());
            packedBoxDTO.setY(packedBox.getY());
            packedBoxDTO.setZ(packedBox.getZ());
            packedBoxDTOs.add(packedBoxDTO);
        }
        resultDTO.setPackedBoxes(packedBoxDTOs);
        return resultDTO;
    }
}

