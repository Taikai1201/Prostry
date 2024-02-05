package ca.group06.sellservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
