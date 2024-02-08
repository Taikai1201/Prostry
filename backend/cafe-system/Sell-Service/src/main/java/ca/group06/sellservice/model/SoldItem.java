package ca.group06.sellservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_sellings")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SoldItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int quantity;

    @Builder.Default
    private LocalDate soldAt = LocalDate.now();
    private BigDecimal price;
}
