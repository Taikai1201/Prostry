package ca.group06.batchservice.service;

import ca.group06.batchservice.dto.type.CreateTypeRequest;
import ca.group06.batchservice.dto.type.TypeInfo;
import ca.group06.batchservice.dto.type.UpdateTypeRequest;
import ca.group06.batchservice.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ca.group06.batchservice.model.Type;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;

    public ResponseEntity<?> createTypeRecord(CreateTypeRequest request) {
        log.info("Creating new type record with name {}", request.getName());

        Type type = typeRepository.findByName(request.getName()).orElse(null);

        if (type != null) {
            log.warn("Type with such name already exists.");
            return new ResponseEntity<>("Type with such name already exists.",
                    HttpStatus.BAD_REQUEST);
        }

        type = Type.builder()
                .name(request.getName())
                .storeDays(request.getStoreDays())
                .build();

        typeRepository.save(type);
        log.info("New record record has been created");
        return new ResponseEntity<>("New record record has been created",
                HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllTypeRecords() {
        log.info("Getting all type records");
        return new ResponseEntity<>(typeRepository.findAll()
                .stream()
                .map(this::mapToTypeInfo)
                .toList(), HttpStatus.OK);
    }

    public ResponseEntity<?> getTypeRecord(UUID id) {

        log.info("Getting type info for ID: {}", id);

        Type type = typeRepository.findById(id).orElse(null);

        if (type == null) {
            log.error("No type record with such ID: {}", id);
            return new ResponseEntity<>("No type record with such ID: " + id,
                    HttpStatus.BAD_REQUEST);
        }

        log.info("Returning type info");
        return new ResponseEntity<>("Returning type info", HttpStatus.OK);
    }

    public ResponseEntity<?> updateTypeRecord(UUID id, UpdateTypeRequest request) {

        log.info("Updating type record with ID: {}", id);

        Type type = typeRepository.findById(id).orElse(null);

        if (type == null) {
            log.error("No type record with such ID: {}", id);
            return new ResponseEntity<>("No type record with such ID: " + id,
                    HttpStatus.BAD_REQUEST);
        }

        type.setName(request.getName());
        type.setStoreDays(request.getStoreDays());

        typeRepository.save(type);
        log.info("Type record updated");
        return new ResponseEntity<>("Type record updated", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteTypeRecord(UUID id) {

        log.info("Deleting type record with ID: {}", id);

        typeRepository.deleteById(id);

        log.info("Deleted type record with ID: {}", id);
        return new ResponseEntity<>("Deleted type record with ID: " + id,
                HttpStatus.OK);
    }

    public int getShelfLife(UUID typeId) {

        log.info("Getting shelf life for ID: {}", typeId);
        Type type = typeRepository.findById(typeId).orElse(null);

        if (type == null) {
            log.error("Unknown type ID.");
            return -1;
        }

        return type.getStoreDays();
    }

    public Type getTypeReference(UUID typeId) {
        return typeRepository.getReferenceById(typeId);
    }

    TypeInfo mapToTypeInfo(Type type) {
        return TypeInfo.builder()
                .id(type.getId())
                .name(type.getName())
                .storeDays(type.getStoreDays())
                .build();
    }

}
