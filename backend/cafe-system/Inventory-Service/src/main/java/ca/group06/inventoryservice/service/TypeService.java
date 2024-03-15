package ca.group06.inventoryservice.service;

import ca.group06.inventoryservice.dto.type.CreateTypeRequest;
import ca.group06.inventoryservice.dto.type.TypeInfo;
import ca.group06.inventoryservice.dto.type.UpdateTypeRequest;
import ca.group06.inventoryservice.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ca.group06.inventoryservice.model.Type;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;

    public void createTypeRecord(CreateTypeRequest request) {
        log.info("Creating new type record with name {}", request.getName());

        Optional<Type> type = typeRepository.findByName(request.getName());

        if (type.isPresent()) {
            log.warn("Type with such name already exists.");
            throw new InvalidParameterException(
                    String.format("Type with such name already exists: %s",
                            request.getName()));
        }

        Type newType = Type.builder()
                .name(request.getName())
                .storeDays(request.getStoreDays())
                .build();

        typeRepository.save(newType);
        log.info("New record record has been created");
    }

    public List<TypeInfo> getAllTypeRecords() {
        log.info("Getting all type records");
        return typeRepository.findAll()
                .stream()
                .map(this::mapToTypeInfo)
                .toList();
    }

    public TypeInfo getTypeRecord(UUID id) {

        log.info("Getting type info for ID: {}", id);

        Type type = typeRepository.findById(id).orElseThrow(
                () -> {
                    log.error("No type record with such ID: {}", id);
                    return new InvalidParameterException(
                            String.format("No type record with such ID: %s", id));
                }
        );
        log.info("Returning type info");
        return mapToTypeInfo(type);
    }

    public void updateTypeRecord(UUID id, UpdateTypeRequest request) {

        log.info("Updating type record with ID: {}", id);

        Type type = typeRepository.findById(id).orElseThrow(() -> {
            log.error("No type record with such ID: {}", id);
            return new InvalidParameterException(
                    String.format("No type record with such ID: %s", id));
        });

        type.setName(request.getName());
        type.setStoreDays(request.getStoreDays());

        typeRepository.save(type);
        log.info("Type record updated");
    }

    public void deleteTypeRecord(UUID id) {
        log.info("Deleting type record with ID: {}", id);
        typeRepository.deleteById(id);
    }

    /**
     * @param typeId
     * @return Shelf life for provided typeID or -1 if type was not found
     */
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
