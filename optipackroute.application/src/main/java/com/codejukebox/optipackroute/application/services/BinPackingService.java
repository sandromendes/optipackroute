package com.codejukebox.optipackroute.application.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.AnxietatemAlgorithm;
import com.codejukebox.optipackroute.core.algorithms.binpacking.bestfit.BestFitDecreasingHeightPacker;
import com.codejukebox.optipackroute.core.algorithms.binpacking.bottomleftback.BottomLeftBackPacker;
import com.codejukebox.optipackroute.core.algorithms.binpacking.extremepoint.ExtremePointBased_AFITPacker;
import com.codejukebox.optipackroute.core.algorithms.binpacking.guillotinesplit.GuillotineSplitPacker;
import com.codejukebox.optipackroute.domain.models.binpacking.AnxietatemResultDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.BoxDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.ContainerDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.Dimensions;
import com.codejukebox.optipackroute.domain.models.binpacking.PackedBoxDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResult;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResultDTO;

@Service
public class BinPackingService {

    private static final Logger logger = LoggerFactory.getLogger(BinPackingService.class);

    private final AnxietatemAlgorithm anxietatemAlgorithm;

    public BinPackingService(AnxietatemAlgorithm anxietatemAlgorithm) {
        this.anxietatemAlgorithm = anxietatemAlgorithm;
    }
    
    public PackingResultDTO packUsingBottomLeftBack(ContainerDTO containerDTO) {
        logger.info("Starting Bottom Left Back packing for container: {}", containerDTO);
        try {
            var packer = new BottomLeftBackPacker(
                    containerDTO.getLength(),
                    containerDTO.getHeight(),
                    containerDTO.getDepth()
            );
            var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
            packer.loadAvailableBoxes(boxes);
            var result = packer.pack();
            logger.info("Packing completed successfully: {}", result);
            return mapPackingResultToDTO(result);
        } catch (Exception e) {
            logger.error("Error during Bottom Left Back packing: {}", e.getMessage(), e);
            throw new RuntimeException("Packing failed: " + e.getMessage(), e);
        }
    }

    public PackingResultDTO packUsingBestFitDecreasingHeight(ContainerDTO containerDTO) {
        logger.info("Starting Best Fit Decreasing Height packing for container: {}", containerDTO);
        try {
            var packer = new BestFitDecreasingHeightPacker(
                    containerDTO.getLength(),
                    containerDTO.getHeight(),
                    containerDTO.getDepth()
            );
            var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
            packer.loadAvailableBoxes(boxes);
            var result = packer.pack();
            logger.info("Packing completed successfully: {}", result);
            return mapPackingResultToDTO(result);
        } catch (Exception e) {
            logger.error("Error during Best Fit Decreasing Height packing: {}", e.getMessage(), e);
            throw new RuntimeException("Packing failed: " + e.getMessage(), e);
        }
    }

    public PackingResultDTO packUsingGuillotineSplit(ContainerDTO containerDTO) {
        logger.info("Starting Guillotine Split packing for container: {}", containerDTO);
        try {
            var packer = new GuillotineSplitPacker(
                    containerDTO.getLength(),
                    containerDTO.getHeight(),
                    containerDTO.getDepth()
            );
            var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
            packer.loadAvailableBoxes(boxes);
            var result = packer.pack();
            logger.info("Packing completed successfully: {}", result);
            return mapPackingResultToDTO(result);
        } catch (Exception e) {
            logger.error("Error during Guillotine Split packing: {}", e.getMessage(), e);
            throw new RuntimeException("Packing failed: " + e.getMessage(), e);
        }
    }

    public PackingResultDTO packUsingExtremePointBasedAFIT(ContainerDTO containerDTO) {
        logger.info("Starting Extreme Point Based AFIT packing for container: {}", containerDTO);
        try {
            var packer = new ExtremePointBased_AFITPacker(
                    containerDTO.getLength(),
                    containerDTO.getHeight(),
                    containerDTO.getDepth()
            );
            var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
            packer.loadAvailableBoxes(boxes);
            var result = packer.pack();
            logger.info("Packing completed successfully: {}", result);
            return mapPackingResultToDTO(result);
        } catch (Exception e) {
            logger.error("Error during Extreme Point Based AFIT packing: {}", e.getMessage(), e);
            throw new RuntimeException("Packing failed: " + e.getMessage(), e);
        }
    }

    private List<Box> mapBoxDTOToBox(List<BoxDTO> boxDTOList) {
        logger.info("Mapping BoxDTOs to Box objects.");
        var boxes = new ArrayList<Box>();
        for (BoxDTO dto : boxDTOList) {
            boxes.add(new Box(dto.getLabel(), dto.getHeight(), dto.getDepth(), dto.getLength()));
        }
        return boxes;
    }
    
    public AnxietatemResultDTO packUsingAnxietatem(ContainerDTO containerDTO) {
        logger.info("Starting Anxietatem packing for container: {}", containerDTO);
        try {          
            var dimension = new Dimensions(containerDTO.getLength(), containerDTO.getHeight(), containerDTO.getDepth());
            
            anxietatemAlgorithm.setup(dimension, containerDTO.getBoxes().size());
            
            var boxes = mapBoxDTOToBox(containerDTO.getBoxes());
            
            anxietatemAlgorithm.loadAvailableBoxes(boxes);
            
            return anxietatemAlgorithm.pack();
        } catch (Exception e) {
            logger.error("Error during Anxietatem packing: {}", e.getMessage(), e);
            throw new RuntimeException("Packing failed: " + e.getMessage(), e);
        }
    }

    private PackingResultDTO mapPackingResultToDTO(PackingResult result) {
        logger.info("Mapping PackingResult to PackingResultDTO.");
        var resultDTO = new PackingResultDTO();
        resultDTO.setContainerLength(result.getContainerLength());
        resultDTO.setContainerHeight(result.getContainerHeight());
        resultDTO.setContainerWidth(result.getContainerDepth());

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


