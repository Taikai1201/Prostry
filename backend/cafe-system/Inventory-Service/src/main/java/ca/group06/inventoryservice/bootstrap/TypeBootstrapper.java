package ca.group06.inventoryservice.bootstrap;

import ca.group06.inventoryservice.model.Type;
import ca.group06.inventoryservice.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TypeBootstrapper implements DevDataBootstrapper {

    private final TypeRepository typeRepository;

    @Override
    public void bootstrap() {
        log.info("Bootstrapping types");

        Type cookie = Type.builder()
                .name("Cookies")
                .storeDays(5)
                .build();

        Type bread = Type.builder()
                .name("Bread")
                .storeDays(6)
                .build();

        Type muffins = Type.builder()
                .name("Muffins")
                .storeDays(3)
                .build();

        Type sandwiches = Type.builder()
                .name("Sandwiches")
                .storeDays(1)
                .build();

        typeRepository.saveAll(List.of(cookie, bread, muffins, sandwiches));

    }
}
