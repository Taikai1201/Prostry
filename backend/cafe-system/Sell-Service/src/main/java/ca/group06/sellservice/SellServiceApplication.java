package ca.group06.sellservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SellServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellServiceApplication.class, args);
    }

}
