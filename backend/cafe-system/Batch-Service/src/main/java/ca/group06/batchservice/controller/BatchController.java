package ca.group06.batchservice.controller;

import ca.group06.batchservice.dto.batch.CreateBatchRequest;
import ca.group06.batchservice.dto.batch.SellBatchUpdateRequest;
import ca.group06.batchservice.dto.batch.UpdateBatchRequest;
import ca.group06.batchservice.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/batch-service/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping
    public ResponseEntity<String> createBatchRecord(@RequestBody CreateBatchRequest request) {
        return batchService.createBatchRecord(request);
    }

    @GetMapping("/{qrCodeId}")
    public ResponseEntity<?> getBatchRecord(@PathVariable UUID qrCodeId) {
        return batchService.getBatchRecord(qrCodeId);
    }

    @PutMapping("/{batchId}")
    public ResponseEntity<?> updateBatchRecord(@PathVariable UUID batchId, @RequestBody UpdateBatchRequest request) {
        return batchService.updateBatchRecord(batchId, request);
    }

    @DeleteMapping("/{batchId}")
    public ResponseEntity<?> deleteBatchRecord(@PathVariable UUID batchId) {
        return batchService.deleteBatchRecord(batchId);
    }

    @PutMapping("/sell-update")
    public ResponseEntity<?> updateRecordsWithSells(@RequestBody SellBatchUpdateRequest request) {
        return batchService.updateRecordsWithSells(request);
    }
}
