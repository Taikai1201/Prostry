package ca.group06.inventoryservice.controller;

import ca.group06.inventoryservice.dto.type.CreateTypeRequest;
import ca.group06.inventoryservice.dto.type.TypeInfo;
import ca.group06.inventoryservice.dto.type.UpdateTypeRequest;
import ca.group06.inventoryservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @PostMapping
    public ResponseEntity<?> createTypeRecord(@RequestBody CreateTypeRequest request) {

        try {
            typeService.createTypeRecord(request);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("New record record has been created",
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllTypeRecords() {
        List<TypeInfo> typeInfos = typeService.getAllTypeRecords();
        return new ResponseEntity<>(typeInfos, HttpStatus.OK);
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<?> getTypeRecord(@PathVariable UUID typeId) {

        TypeInfo typeInfo;

        try {
            typeInfo = typeService.getTypeRecord(typeId);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(typeInfo, HttpStatus.OK);
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<?> updateTypeRecord(@PathVariable UUID typeId, @RequestBody UpdateTypeRequest request) {

        try {
            typeService.updateTypeRecord(typeId, request);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Type record updated", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTypeRecord(UUID id) {
        typeService.deleteTypeRecord(id);
        return ResponseEntity.ok().build();
    }

}
