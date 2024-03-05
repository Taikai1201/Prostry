package ca.group06.sellservice.service;

import ca.group06.sellservice.dto.BatchServiceSellsUpdateRequest;
import ca.group06.sellservice.dto.CreateSoldItemRequest;
import ca.group06.sellservice.dto.SoldItemDto;
import ca.group06.sellservice.dto.UpdateSoldItemRequest;
import ca.group06.sellservice.model.SoldItem;
import ca.group06.sellservice.repository.SoldItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.security.InvalidParameterException;
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

    public void createSoldItemRecord(CreateSoldItemRequest request) {

        log.info("Creating new sold item record");

        SoldItem soldItem = SoldItem.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        soldItemRepository.save(soldItem);
        log.info("New record has been saved");
    }

    public void updateSoldItemRecord(UUID id, UpdateSoldItemRequest request) {

        log.info("Updating sold item record with ID: {}", id);

        SoldItem soldItem = soldItemRepository.findById(id).orElseThrow(() -> {
            log.error("No sold record with such ID: {}", id);
            return new InvalidParameterException(String.format("No sold record with such ID: %s", id));
        });

        soldItem.setName(request.getName());
        soldItem.setQuantity(request.getQuantity());
        soldItem.setPrice(request.getPrice());

        soldItemRepository.save(soldItem);
        log.info("Sold record updated");

    }

    public void deleteSoldItemRecord(UUID id) {

        log.info("Deleting sold record with ID: {}", id);
        soldItemRepository.deleteById(id);
    }

    public List<SoldItemDto> getAllSoldItemRecords() {

        log.info("Getting all sold item records");

        List<SoldItem> soldItems = soldItemRepository.findAll();
        return soldItems.stream()
                .map(this::mapToSoldItemDto)
                .toList();
    }

    // TODO: Move to kafka service
    public void updateBatchServiceWithSellingInfo() {

        log.info("Preparing info to send Batch Service request to update records");

        List<SoldItem> soldItems = soldItemRepository.findAllBySoldAt(LocalDate.now());
        BatchServiceSellsUpdateRequest request = mapToSellUpdateRequest(soldItems);

        // Sending request to Batch Service
        restClient.put()
                .uri(batchServiceUrl + "/batches/sell-update")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    private BatchServiceSellsUpdateRequest mapToSellUpdateRequest(List<SoldItem> soldItems) {
        List<BatchServiceSellsUpdateRequest.SellInfo> sellInfos = soldItems.stream()
                .map(soldItem ->
                        BatchServiceSellsUpdateRequest.SellInfo.builder()
                                .soldItemName(soldItem.getName())
                                .quantity(soldItem.getQuantity())
                                .build()
                )
                .toList();
        return BatchServiceSellsUpdateRequest.builder()
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
