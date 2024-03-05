package ca.group06.batchservice.controller;

import ca.group06.batchservice.dto.batch.BatchDto;
import ca.group06.batchservice.dto.batch.CreateBatchRequest;
import ca.group06.batchservice.dto.batch.SoldBatchUpdateRequest;
import ca.group06.batchservice.dto.batch.UpdateBatchRequest;
import ca.group06.batchservice.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping
    public ResponseEntity<String> createBatchRecord(@RequestBody CreateBatchRequest request) {
        try {
            batchService.createBatchRecord(request);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("New batch created", HttpStatus.OK);
    }

    @GetMapping("/{qrCodeId}")
    public ResponseEntity<?> getBatchRecord(@PathVariable UUID qrCodeId) {

        BatchDto batch;
        try {
            batch = batchService.getBatchRecord(qrCodeId);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(batch, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllBatchRecords() {

        List<BatchDto> batchDtos = batchService.getAllBatchRecords();
        return new ResponseEntity<>(batchDtos, HttpStatus.OK);
    }

    @PutMapping("/{batchId}")
    public ResponseEntity<?> updateBatchRecord(@PathVariable UUID batchId, @RequestBody UpdateBatchRequest request) {
        try {
            batchService.updateBatchRecord(batchId, request);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{batchId}")
    public ResponseEntity<?> deleteBatchRecord(@PathVariable UUID batchId) {
        batchService.deleteBatchRecord(batchId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sell-update")
    public ResponseEntity<?> updateRecordsWithSells(@RequestBody SoldBatchUpdateRequest request) {
        return new ResponseEntity<>("Records updated with latest selling info",
                HttpStatus.OK);
    }
}
