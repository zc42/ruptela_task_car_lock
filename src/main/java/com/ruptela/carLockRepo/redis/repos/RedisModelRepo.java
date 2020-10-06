package com.ruptela.carLockRepo.redis.repos;

import com.ruptela.carLockRepo.entity.Model;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisModelRepo extends RedisRepo<String, Model> {

    private final RedisTemplate<String, Model> rt;

    public RedisModelRepo(RedisTemplate<String, Model> rt) {
        super("MODEL", rt.opsForHash(), e -> e.getId());
        this.rt = rt;
    }

    @Override
    public void save(Model v) {
        super.save(rt, v);
    }
}
