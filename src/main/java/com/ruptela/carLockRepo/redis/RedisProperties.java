package com.ruptela.carLockRepo.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class RedisProperties {

    @Value("${resource.redis_rest_api_data_time_out}")
    private Integer timeOut;
    @Value("${resource.redis_host}")
    private String host;
    @Value("${resource.redis_port}")
    private Integer port;

}
