package com.ruptela.car_repo.redis.repos;

import com.ruptela.car_repo.entity.Maker;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisMakerRepo extends RedisRepo<String, Maker> {

    private final RedisTemplate<String, Maker> rt;

    public RedisMakerRepo(RedisTemplate<String, Maker> rt) {
        super("MAKER", rt.opsForHash(), e -> e.getID());
        this.rt = rt;
    }

    @Override
    public void save(Maker v) {
        super.save(rt, v);
    }
}
