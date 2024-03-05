package ca.group06.sellservice.controller;

import ca.group06.sellservice.dto.CreateSoldItemRequest;
import ca.group06.sellservice.dto.SoldItemDto;
import ca.group06.sellservice.dto.UpdateSoldItemRequest;
import ca.group06.sellservice.service.SoldItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sell-service/sells")
@RequiredArgsConstructor
public class SoldItemController {

    private final SoldItemService soldItemService;

    @PostMapping
    public ResponseEntity<?> createSoldItemRecord(@RequestBody CreateSoldItemRequest request) {
        soldItemService.createSoldItemRecord(request);
        return new ResponseEntity<>("New record has been saved", HttpStatus.CREATED);
    }

    @PutMapping("/{soldItemId}")
    public ResponseEntity<?> updateSoldItemRecord(@PathVariable UUID soldItemId,
                                                  @RequestBody UpdateSoldItemRequest request) {
        try {
            soldItemService.updateSoldItemRecord(soldItemId, request);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Sold record updated", HttpStatus.OK);
    }

    @DeleteMapping("/{soldItemId}")
    public ResponseEntity<?> deleteSoldItemRecord(@PathVariable UUID soldItemId) {
        soldItemService.deleteSoldItemRecord(soldItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllSoldItemRecords() {
        List<SoldItemDto> soldItemDtos = soldItemService.getAllSoldItemRecords();
        return new ResponseEntity<>(soldItemDtos, HttpStatus.OK);
    }

    @PostMapping("/batch-update")
    public ResponseEntity<?> updateBatchServiceWithSellingInfo() {
        soldItemService.updateBatchServiceWithSellingInfo();
        return ResponseEntity.ok().build();
    }

}
