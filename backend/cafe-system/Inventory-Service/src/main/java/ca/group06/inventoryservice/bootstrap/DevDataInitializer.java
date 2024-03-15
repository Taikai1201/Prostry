package ca.group06.inventoryservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    private final BatchBootstrapper batchBootstrapper;
    private final TypeBootstrapper typeBootstrapper;

    @Override
    public void run(String... args) throws Exception {
        typeBootstrapper.bootstrap();
        batchBootstrapper.bootstrap();
    }
}
