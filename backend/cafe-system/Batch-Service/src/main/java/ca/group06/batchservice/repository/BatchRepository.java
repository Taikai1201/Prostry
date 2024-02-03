package ca.group06.batchservice.repository;

import ca.group06.batchservice.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatchRepository extends JpaRepository<Batch, UUID> {
}