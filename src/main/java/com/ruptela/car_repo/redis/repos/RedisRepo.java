package com.ruptela.car_repo.redis.repos;

import com.ruptela.car_repo.entity.Model;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

abstract public class RedisRepo<ID, V> {

    @Value("${resource.redis_rest_api_data_time_out}")
    private Integer time_out;

    protected final String key;
    protected final RedisTemplate<String, V> redisTemplate;
    protected final HashOperations hashOperations;
    private final Function<V, String> f;

    public RedisRepo(RedisTemplate<String, V> redisTemplate,
                     String key,
                     Function<V, String> f) {
        this.key = key;
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
        this.f = f;
    }

    public void save(V v) {
        redisTemplate.opsForHash().put(key, f.apply(v), v);
    }

    public void save_with_time_out(V v) {
        save(v);
        redisTemplate.expire(key, time_out, TimeUnit.MINUTES);
    }

    public Map<String, Model> findAll() {
        return hashOperations.entries(key);
    }

    public V findById(ID id) {
        return (V) hashOperations.get(key, id);
    }

    public void update(V v) {
        save(v);
    }

    public void delete(ID id) {
        hashOperations.delete(key, id);
    }
}
