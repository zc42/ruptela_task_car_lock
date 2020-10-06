package com.ruptela.carLockRepo.redis;

import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.entity.Maker;
import com.ruptela.carLockRepo.entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties props;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lcf = new LettuceConnectionFactory();
        lcf.setHostName(props.getHost());
        lcf.setPort(props.getPort());
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
