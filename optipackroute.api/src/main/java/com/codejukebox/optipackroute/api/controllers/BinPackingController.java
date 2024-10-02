package com.codejukebox.optipackroute.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codejukebox.optipackroute.application.services.BinPackingService;
import com.codejukebox.optipackroute.domain.models.binpacking.AnxietatemResultDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.ContainerDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResultDTO;

@RestController
@RequestMapping("/api/v1/bin-packing")
public class BinPackingController {

    private static final Logger logger = LoggerFactory.getLogger(BinPackingController.class);

    @Autowired
    private BinPackingService binPackingService;

    @PostMapping("/bottom-left-back")
    public ResponseEntity<PackingResultDTO> packBottomLeftBack(@RequestBody ContainerDTO containerDTO) {
        logger.info("Starting packing with Bottom Left Back for container: {}", containerDTO);
        try {
            var result = binPackingService.packUsingBottomLeftBack(containerDTO);
            logger.info("Packing completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during packing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/best-fit-decreasing-height")
    public ResponseEntity<PackingResultDTO> packBestFitDecreasingHeight(@RequestBody ContainerDTO containerDTO) {
        logger.info("Starting packing with Best Fit Decreasing Height for container: {}", containerDTO);
        try {
            var result = binPackingService.packUsingBestFitDecreasingHeight(containerDTO);
            logger.info("Packing completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during packing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/guillotine-split")
    public ResponseEntity<PackingResultDTO> packGuillotineSplit(@RequestBody ContainerDTO containerDTO) {
        logger.info("Starting packing with Guillotine Split for container: {}", containerDTO);
        try {
            var result = binPackingService.packUsingGuillotineSplit(containerDTO);
            logger.info("Packing completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during packing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/extreme-point-based-afit")
    public ResponseEntity<PackingResultDTO> packExtremePointBasedAFIT(@RequestBody ContainerDTO containerDTO) {
        logger.info("Starting packing with Extreme Point Based AFIT for container: {}", containerDTO);
        try {
            var result = binPackingService.packUsingExtremePointBasedAFIT(containerDTO);
            logger.info("Packing completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during packing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/anxietatem")
    public ResponseEntity<AnxietatemResultDTO> packAnxietatem(@RequestBody ContainerDTO containerDTO) {
        logger.info("Starting packing with Anxietatem for container: {}", containerDTO);
        try {
            var result = binPackingService.packUsingAnxietatem(containerDTO);
            logger.info("Packing completed successfully: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during packing: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



