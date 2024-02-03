package ca.group06.batchservice.repository;

import ca.group06.batchservice.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeRepository extends JpaRepository<Type, UUID> {
}