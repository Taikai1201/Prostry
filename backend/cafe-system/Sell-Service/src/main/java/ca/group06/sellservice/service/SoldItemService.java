package ca.group06.sellservice.service;

import ca.group06.sellservice.dto.BatchServiceSellUpdateRequest;
import ca.group06.sellservice.dto.CreateSoldItemRequest;
import ca.group06.sellservice.dto.SoldItemDto;
import ca.group06.sellservice.dto.UpdateSoldItemRequest;
import ca.group06.sellservice.model.SoldItem;
import ca.group06.sellservice.repository.SoldItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SoldItemService {

    private final SoldItemRepository soldItemRepository;

    private final RestClient restClient;

    private final String batchServiceUrl = "http://localhost:8180/api/batch-service";

    public ResponseEntity<?> createSoldItemRecord(CreateSoldItemRequest request) {

        log.info("Creating new sold item record");

        SoldItem soldItem = SoldItem.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        soldItemRepository.save(soldItem);
        log.info("New record has been saved");
        return new ResponseEntity<>("New record has been saved", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateSoldItemRecord(UUID id, UpdateSoldItemRequest request) {

        log.info("Updating sold item record with ID: {}", id);

        SoldItem soldItem = soldItemRepository.findById(id).orElse(null);
        if (soldItem == null) {
            log.error("No sold record with such ID: {}", id);
            return new ResponseEntity<>("No sold record with such ID: " + id,
                    HttpStatus.BAD_REQUEST);
        }

        soldItem.setName(request.getName());
        soldItem.setQuantity(request.getQuantity());
        soldItem.setPrice(request.getPrice());

        soldItemRepository.save(soldItem);
        log.info("Sold record updated");
        return new ResponseEntity<>("Sold record updated", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteSoldItemRecord(UUID id) {

        log.info("Deleting sold record with ID: {}", id);
        soldItemRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> getAllSoldItemRecords() {

        log.info("Getting all sold item records");

        List<SoldItem> soldItems = soldItemRepository.findAll();

        return new ResponseEntity<>(
                soldItems.stream()
                        .map(this::mapToSoldItemDto)
                        .toList(),
                HttpStatus.OK);
    }

    public ResponseEntity<?> updateBatchServiceWithSellingInfo() {

        log.info("Preparing info to send Batch Service request to update records");

        List<SoldItem> soldItems = soldItemRepository.findAllBySoldAt(LocalDate.now());
        BatchServiceSellUpdateRequest request = mapToSellUpdateRequest(soldItems);

        // Sending request to Batch Service
        restClient.put()
                .uri(batchServiceUrl + "/batches/sell-update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();

        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    private BatchServiceSellUpdateRequest mapToSellUpdateRequest(List<SoldItem> soldItems) {
        List<BatchServiceSellUpdateRequest.SellInfo> sellInfos = soldItems.stream()
                .map(soldItem ->
                        BatchServiceSellUpdateRequest.SellInfo.builder()
                                .soldItemName(soldItem.getName())
                                .quantity(soldItem.getQuantity())
                                .build()
                )
                .toList();
        return BatchServiceSellUpdateRequest.builder()
                .sellInfos(sellInfos)
                .build();
    }

    private SoldItemDto mapToSoldItemDto(SoldItem soldItem) {
        return SoldItemDto.builder()
                .id(soldItem.getId())
                .name(soldItem.getName())
                .quantity(soldItem.getQuantity())
                .soldAt(soldItem.getSoldAt())
                .price(soldItem.getPrice())
                .build();
    }
}
