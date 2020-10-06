package com.ruptela.carLockRepo.rest_client;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@NoArgsConstructor
public class VehileConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
