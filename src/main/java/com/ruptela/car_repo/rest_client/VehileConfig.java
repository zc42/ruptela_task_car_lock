package com.ruptela.car_repo.rest_client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VehileConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
