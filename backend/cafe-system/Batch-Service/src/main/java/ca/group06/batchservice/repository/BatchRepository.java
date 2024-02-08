package ca.group06.batchservice.repository;

import ca.group06.batchservice.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BatchRepository extends JpaRepository<Batch, UUID> {

    Optional<Batch> findByQrCodeId(UUID qrCodeId);

    List<Batch> findAllByNameInAndQuantityGreaterThan(List<String> names, int quantity);

}