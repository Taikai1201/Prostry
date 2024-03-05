package ca.group06.batchservice.service;

import ca.group06.batchservice.dto.batch.BatchDto;
import ca.group06.batchservice.dto.batch.CreateBatchRequest;
import ca.group06.batchservice.dto.batch.SoldBatchUpdateRequest;
import ca.group06.batchservice.dto.batch.UpdateBatchRequest;
import ca.group06.batchservice.model.Batch;
import ca.group06.batchservice.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchService {

    private final BatchRepository batchRepository;

    private final TypeService typeService;

    public void createBatchRecord(CreateBatchRequest request) {

        log.info("Creating new batch record. {}", request.toString());

        // Building new batch entity with provided data
        Batch batch = Batch.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .qrCodeId(request.getQrCodeId())
                .build();

        // Getting shelf life from typeService
        int storeDays = typeService.getShelfLife(request.getTypeId());
        if (storeDays == -1) {
            log.error("Unknown type ID");
            throw new InvalidParameterException(String.format("Unknown typeID: %s", request.getTypeId()));
        }

        // Setting bestBefore (createdAt + storeDays) and type
        batch.setBestBefore(batch.getCreatedAt().plusDays(storeDays));
        batch.setType(typeService.getTypeReference(request.getTypeId()));

        batchRepository.save(batch);
        log.info("New record saved");
    }

    public BatchDto getBatchRecord(UUID qrCodeId) {

        log.info("Reading record with QR-code-ID: {}", qrCodeId);

        // Getting batch record from DB using qrCodeId
        Batch batch = batchRepository.findByQrCodeId(qrCodeId).orElseThrow(
                () -> {
                    log.error("No record with such QR-Code-ID: {}", qrCodeId);
                    return new InvalidParameterException(String.format("No record with such QR-Code-ID: %s", qrCodeId));
                }
        );

        log.info("Returning record with ID: {}", batch.getId());
        return mapToBatchDto(batch);
    }

    public List<BatchDto> getAllBatchRecords() {
        log.info("Getting all batch records");

        return batchRepository.findAll()
                .stream()
                .map(this::mapToBatchDto)
                .toList();
    }

    public void updateBatchRecord(UUID id, UpdateBatchRequest request) {

        log.info("Updating batch record with ID: {}", id);

        // Getting batch record using ID
        Batch batch = batchRepository.findById(id).orElseThrow(
                () -> {
                    log.error("No record with such ID: {}", id);
                    return new InvalidParameterException(String.format("No record with such ID: %s", id));
                }
        );

        // Updating fields
        batch.setName(request.getName());
        batch.setQuantity(request.getQuantity());
        batch.setQrCodeId(request.getQrCodeId());
        batchRepository.save(batch);
        log.info("Record has been updated");
    }

    public void deleteBatchRecord(UUID id) {
        log.info("Deleting record with ID: {}", id);
        batchRepository.deleteById(id);
    }

    public void updateRecordsWithSells(SoldBatchUpdateRequest request) {

        log.info("Updating batch records with selling info");

        // Extract sold items names from request into List<String>
        List<String> soldItemsNames = request.getSellInfos().stream()
                .map(SoldBatchUpdateRequest.SellInfo::getSoldItemName)
                .toList();

        // Getting all records with provided names from DB
        List<Batch> batches = batchRepository.findAllByNameInAndQuantityGreaterThan(soldItemsNames, 0);

        // Sorting all records Increase based on createdAt and grouping based on record name field
        Map<String, List<Batch>> groupedBatches = batches.stream()
                .sorted(Comparator.comparing(Batch::getCreatedAt))
                .collect(groupingBy(Batch::getName));

        // Iterating through sellInfo
        for (SoldBatchUpdateRequest.SellInfo sellInfo : request.getSellInfos()) {
            // Getting list of records for each name from request
            for (var batch : groupedBatches.get(sellInfo.getSoldItemName())) {
                int batchQuantity = batch.getQuantity();
                int remainder = sellInfo.getQuantity() - batchQuantity;

                // If remainder less or equal zero => we have cycled through all necessary records,
                // we should leave this group
                if (remainder <= 0) {
                    batch.setQuantity(batchQuantity - sellInfo.getQuantity());
                    batchRepository.save(batch);
                    break;
                }
                // if it's less than zero => there are more records to update
                else {
                    batch.setQuantity(0);
                    batchRepository.save(batch);
                    sellInfo.setQuantity(sellInfo.getQuantity() - batchQuantity);
                }
            }
        }

    }

    BatchDto mapToBatchDto(Batch batch) {
        return BatchDto.builder()
                .id(batch.getId())
                .name(batch.getName())
                .type(typeService.mapToTypeInfo(batch.getType()))
                .createdAt(batch.getCreatedAt())
                .bestBefore(batch.getBestBefore())
                .quantity(batch.getQuantity())
                .qrCodeId(batch.getQrCodeId())
                .build();

    }
}
