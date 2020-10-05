package com.ruptela.car_repo.redis;

import com.ruptela.car_repo.entity.Model;
import java.util.Map;
import java.util.function.Function;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

abstract public class RedisRepo<ID, V> {

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
