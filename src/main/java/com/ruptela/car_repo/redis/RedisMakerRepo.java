package com.ruptela.car_repo.redis;

import com.ruptela.car_repo.entity.Maker;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisMakerRepo extends RedisRepo<String, Maker> {

    public RedisMakerRepo(RedisTemplate<String, Maker> redisTemplate) {
        super(redisTemplate, "MAKER", e -> e.getID());
    }

    @Override
    public void save(Maker v) {
        super.save(v);
        super.redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }
}
