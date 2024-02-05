package ca.group06.sellservice.repository;

import ca.group06.sellservice.model.SoldItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SoldItemRepository extends JpaRepository<SoldItem, UUID> {

    List<SoldItem> findAllBySoldAt(LocalDate date);

}
