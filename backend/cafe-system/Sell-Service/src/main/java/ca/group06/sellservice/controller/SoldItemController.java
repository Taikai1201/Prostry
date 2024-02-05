package ca.group06.sellservice.controller;

import ca.group06.sellservice.dto.CreateSoldItemRequest;
import ca.group06.sellservice.dto.UpdateSoldItemRequest;
import ca.group06.sellservice.service.SoldItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sell-service/sells")
@RequiredArgsConstructor
public class SoldItemController {

    private final SoldItemService soldItemService;

    @PostMapping
    public ResponseEntity<?> createSoldItemRecord(@RequestBody CreateSoldItemRequest request) {
        return soldItemService.createSoldItemRecord(request);
    }

    @PutMapping("/{soldItemId}")
    public ResponseEntity<?> updateSoldItemRecord(@PathVariable UUID soldItemId,
                                                  @RequestBody UpdateSoldItemRequest request) {
        return soldItemService.updateSoldItemRecord(soldItemId, request);
    }

    @DeleteMapping("/{soldItemId}")
    public ResponseEntity<?> deleteSoldItemRecord(@PathVariable UUID soldItemId) {
        return soldItemService.deleteSoldItemRecord(soldItemId);
    }

    @GetMapping
    public ResponseEntity<?> getAllSoldItemRecords() {
        return soldItemService.getAllSoldItemRecords();
    }

    @PostMapping("/batch-update")
    public ResponseEntity<?> updateBatchServiceWithSellingInfo() {
        return soldItemService.updateBatchServiceWithSellingInfo();
    }

}
