package com.ruptela.carLockRepo.redis.repos;

import com.ruptela.carLockRepo.entity.Maker;
import com.ruptela.carLockRepo.redis.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisMakerRepo extends RedisRepo<String, Maker> {

    @Autowired
    private RedisProperties props;
    private final RedisTemplate<String, Maker> rt;

    public RedisMakerRepo(RedisTemplate<String, Maker> rt) {
        super("MAKER", rt.opsForHash(), e -> e.getMakeName());
        this.rt = rt;
    }

    @Override
    public void save(Maker v) {
        super.save(rt, v, props.getTimeOut());
    }
}
