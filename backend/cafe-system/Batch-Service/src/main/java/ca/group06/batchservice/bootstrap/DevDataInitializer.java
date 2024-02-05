package ca.group06.batchservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    private final BatchBootstrapper batchBootstrapper;

    @Override
    public void run(String... args) throws Exception {
        batchBootstrapper.bootstrap();
    }
}
