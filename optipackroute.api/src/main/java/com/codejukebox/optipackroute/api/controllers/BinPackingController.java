package com.codejukebox.optipackroute.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codejukebox.optipackroute.application.services.BinPackingService;
import com.codejukebox.optipackroute.domain.models.binpacking.ContainerDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.PackingResultDTO;

@RestController
@RequestMapping("/api/v1/bin-packing")
public class BinPackingController {

    @Autowired
    private BinPackingService binPackingService;

    @PostMapping("/bottom-left-back")
    public ResponseEntity<PackingResultDTO> packBottomLeftBack(@RequestBody ContainerDTO containerDTO) {
        var result = binPackingService.packUsingBottomLeftBack(containerDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/best-fit-decreasing-height")
    public ResponseEntity<PackingResultDTO> packBestFitDecreasingHeight(@RequestBody ContainerDTO containerDTO) {
        var result = binPackingService.packUsingBestFitDecreasingHeight(containerDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/guillotine-split")
    public ResponseEntity<PackingResultDTO> packGuillotineSplit(@RequestBody ContainerDTO containerDTO) {
        var result = binPackingService.packUsingGuillotineSplit(containerDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/extreme-point-based-afit")
    public ResponseEntity<PackingResultDTO> packExtremePointBasedAFIT(@RequestBody ContainerDTO containerDTO) {
        var result = binPackingService.packUsingExtremePointBasedAFIT(containerDTO);
        return ResponseEntity.ok(result);
    }
}

