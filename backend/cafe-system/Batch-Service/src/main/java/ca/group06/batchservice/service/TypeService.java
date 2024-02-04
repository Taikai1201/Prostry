package ca.group06.batchservice.service;

import ca.group06.batchservice.dto.type.TypeInfo;
import ca.group06.batchservice.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ca.group06.batchservice.model.Type;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;

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
