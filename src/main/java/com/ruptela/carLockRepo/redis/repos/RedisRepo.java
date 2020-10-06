package com.ruptela.carLockRepo.redis.repos;

import com.ruptela.carLockRepo.entity.Model;
import com.ruptela.carLockRepo.redis.RedisProperties;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

abstract public class RedisRepo<ID, V> {

    @Autowired
    private RedisProperties props;
    private String key;
    private HashOperations hashOperations;
    private Function<V, ID> f;

    public RedisRepo(String key,
                     HashOperations hashOperations,
                     Function<V, ID> f) {
        this.key = key;
        this.hashOperations = hashOperations;
        this.f = f;
    }

    private void _save(V v) {
        hashOperations.put(key, f.apply(v), v);
    }

    public void save(V v) {
        _save(v);
    }

    public void save(RedisTemplate rt, V v) {
        _save(v);
        rt.expire(key, props.getTimeOut(), TimeUnit.MINUTES);
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
