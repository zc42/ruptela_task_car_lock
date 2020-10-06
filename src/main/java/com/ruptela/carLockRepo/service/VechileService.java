package com.ruptela.carLockRepo.service;

import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.entity.Maker;
import com.ruptela.carLockRepo.entity.Model;
import com.ruptela.carLockRepo.redis.repos.RedisMakerRepo;
import com.ruptela.carLockRepo.redis.repos.RedisModelRepo;
import com.ruptela.carLockRepo.rest_client.VehileClient;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VechileService {

    @Autowired
    private VehileClient vechileAPI;
    @Autowired
    private RedisModelRepo modelRepo;
    @Autowired
    private RedisMakerRepo makerRepo;

    public boolean makerModelExist(Car car) {
        boolean b = true;
        b = b && makerExist(car);
        b = b && modelExist(car);
        return b;
    }

    private boolean makerExist(Car car) {
        return exist(car,
                     e -> Maker.from(e),
                     e -> makerRepo.findById(e.getMakeName()),
                     e -> vechileAPI.GetAllMakes(),
                     e -> makerRepo.save(e),
                     e -> e.isExists());
    }

    private boolean modelExist(Car car) {
        return exist(car,
                     e -> Model.from(e),
                     e -> modelRepo.findById(e.getId()),
                     e -> vechileAPI.GetModels(e.getMakeName()),
                     e -> modelRepo.save(e),
                     e -> e.isExists());
    }

//    find e -> redis
//    e ==null -> get all makers -> rest_api
//      save results -> redis
//      find e -> redis
//      not exist -> save e with status not_exits
//    return e exist
    private <T> boolean exist(Car car,
                              Function<Car, T> fMapCar2T,
                              Function<T, T> fRedisFindById,
                              Function<T, List<T>> fRestApi,
                              Consumer<T> fRedisSave,
                              Function<T, Boolean> fExists) {

        T m0 = fMapCar2T.apply(car);
        T m1 = fRedisFindById.apply(m0);

        if (m1 != null) {
            return fExists.apply(m1);
        }

        List<T> l = fRestApi.apply(m0);
        l.forEach(e -> fRedisSave.accept(e));
        m1 = fRedisFindById.apply(m0);

        if (m1 != null) {
            return fExists.apply(m1);
        }

        fRedisSave.accept(m0);
        return fExists.apply(m0);
    }

}
