package com.ruptela.car_repo.redis;

import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.entity.Maker;
import com.ruptela.car_repo.entity.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Value("${resource.redis_host}")
    private String host;
    @Value("${resource.redis_port}")
    private Integer port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lcf = new LettuceConnectionFactory();
        lcf.setHostName(host);
        lcf.setPort(port);
        lcf.afterPropertiesSet();
        return lcf;
    }

    @Bean
    public RedisTemplate<String, Car> carRedisTemplate() {
        return template();
    }

    @Bean
    public RedisTemplate<String, Model> modelRedisTemplate() {
        return template();
    }

    @Bean
    public RedisTemplate<String, Maker> makerRedisTemplate() {
        return template();
    }

    private <T1, T2> RedisTemplate<T1, T2> template() {
        RedisTemplate<T1, T2> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
