package com.ruptela.carLockRepo.redis.repos;

import com.ruptela.carLockRepo.entity.Model;
import com.ruptela.carLockRepo.redis.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisModelRepo extends RedisRepo<String, Model> {

    @Autowired
    private RedisProperties props;
    private final RedisTemplate<String, Model> rt;

    public RedisModelRepo(RedisTemplate<String, Model> rt) {
        super("MODEL", rt.opsForHash(), e -> e.getId());
        this.rt = rt;
    }

    @Override
    public void save(Model v) {
        super.save(rt, v, props.getTimeOut());
    }
}
