package ca.group06.batchservice.controller;

import ca.group06.batchservice.dto.type.CreateTypeRequest;
import ca.group06.batchservice.dto.type.UpdateTypeRequest;
import ca.group06.batchservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/batch-service/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @PostMapping
    public ResponseEntity<?> createTypeRecord(@RequestBody CreateTypeRequest request) {
        return typeService.createTypeRecord(request);
    }

    @GetMapping
    public ResponseEntity<?> getAllTypeRecords() {
        return typeService.getAllTypeRecords();
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<?> getTypeRecord(@PathVariable UUID typeId) {
        return typeService.getTypeRecord(typeId);
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<?> updateTypeRecord(@PathVariable UUID typeId, @RequestBody UpdateTypeRequest request) {
        return typeService.updateTypeRecord(typeId, request);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTypeRecord(UUID id) {
        return typeService.deleteTypeRecord(id);
    }

}
