package com.ruptela.car_repo.redis;

import com.ruptela.car_repo.entity.Model;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisModelRepo extends RedisRepo<String, Model> {

    public RedisModelRepo(RedisTemplate<String, Model> redisTemplate) {
        super(redisTemplate, "MODEL", e -> e.getID());
    }

    @Override
    public void save(Model v) {
        super.save(v);
        super.redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }
}
