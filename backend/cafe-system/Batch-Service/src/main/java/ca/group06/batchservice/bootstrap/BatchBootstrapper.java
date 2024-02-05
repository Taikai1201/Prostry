package ca.group06.batchservice.bootstrap;

import ca.group06.batchservice.model.Batch;
import ca.group06.batchservice.model.Type;
import ca.group06.batchservice.repository.BatchRepository;
import ca.group06.batchservice.repository.TypeRepository;
import ca.group06.batchservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchBootstrapper implements DevDataBootstrapper {

    private final BatchRepository batchRepository;
    private final TypeRepository typeRepository;
    private final TypeService typeService;

    public void bootstrap() {

        log.info("Bootstrapping batches");

        Type cookiesType = typeRepository.findById(
                UUID.fromString("44395ba6-355f-4b26-9a30-cff77aafa7d1"))
                .get();
        Type breadType = typeRepository.findById(
                UUID.fromString("d2b16bb2-843b-40e7-b463-4910cc35dfbe"))
                .get();
        Type muffinsType = typeRepository.findById(
                UUID.fromString("6f4f607b-76a9-432e-817e-8d61a3532c8a"))
                .get();
        Type sandwichesType = typeRepository.findById(
                UUID.fromString("77323bfc-f638-4c55-b9de-54737280f155"))
                .get();

        Batch b1 = Batch.builder()
                .name("Chocolate cookie")
                .type(cookiesType)
                .quantity(20)
                .qrCodeId(UUID.randomUUID())
                .build();
        b1.setBestBefore(b1.getCreatedAt().plusDays(cookiesType.getStoreDays()));

        Batch b2 = Batch.builder()
                .name("White bread")
                .type(breadType)
                .quantity(4)
                .createdAt(LocalDate.of(2024,2,1))
                .qrCodeId(UUID.randomUUID())
                .build();
        b2.setBestBefore(b2.getCreatedAt().plusDays(breadType.getStoreDays()));

        Batch b21 = Batch.builder()
                .name("White bread")
                .type(breadType)
                .quantity(10)
                .createdAt(LocalDate.of(2024,2,2))
                .qrCodeId(UUID.randomUUID())
                .build();
        b21.setBestBefore(b21.getCreatedAt().plusDays(breadType.getStoreDays()));

        Batch b22 = Batch.builder()
                .name("White bread")
                .type(breadType)
                .quantity(10)
                .createdAt(LocalDate.of(2024,2,3))
                .qrCodeId(UUID.randomUUID())
                .build();
        b22.setBestBefore(b22.getCreatedAt().plusDays(breadType.getStoreDays()));

        Batch b3 = Batch.builder()
                .name("Blueberry muffin")
                .type(muffinsType)
                .quantity(13)
                .createdAt(LocalDate.of(2024,2,3))
                .qrCodeId(UUID.randomUUID())
                .build();
        b3.setBestBefore(b3.getCreatedAt().plusDays(muffinsType.getStoreDays()));

        Batch b31 = Batch.builder()
                .name("Blueberry muffin")
                .type(muffinsType)
                .quantity(20)
                .createdAt(LocalDate.of(2024,2,5))
                .qrCodeId(UUID.randomUUID())
                .build();
        b31.setBestBefore(b31.getCreatedAt().plusDays(muffinsType.getStoreDays()));

        Batch b4 = Batch.builder()
                .name("Tuna sandwich")
                .type(sandwichesType)
                .quantity(5)
                .qrCodeId(UUID.randomUUID())
                .build();
        b4.setBestBefore(b4.getCreatedAt().plusDays(sandwichesType.getStoreDays()));

        batchRepository.saveAll(List.of(b1, b2, b21, b22, b3, b31, b4));
        log.info("Batches bootstrapping done");
    }
}
