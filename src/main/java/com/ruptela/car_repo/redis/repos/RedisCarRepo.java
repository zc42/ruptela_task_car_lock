package com.ruptela.car_repo.redis.repos;

import com.ruptela.car_repo.entity.Car;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCarRepo extends RedisRepo<String, Car> {

    public RedisCarRepo(RedisTemplate<String, Car> rt) {
        super("CAR", rt.opsForHash(), e -> e.getVin());
    }

    @Override
    public void save(Car v) {
        super.save(v);
    }
}
