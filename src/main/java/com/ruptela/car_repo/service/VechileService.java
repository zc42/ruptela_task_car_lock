package com.ruptela.car_repo.service;

import com.ruptela.car_repo.rest_client.VechileAPIClient;
import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.entity.Maker;
import com.ruptela.car_repo.entity.Model;
import com.ruptela.car_repo.redis.RedisMakerRepo;
import com.ruptela.car_repo.redis.RedisModelRepo;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VechileService {

    @Autowired
    private VechileAPIClient vechileAPI;
    @Autowired
    private RedisModelRepo modelRepo;
    @Autowired
    private RedisMakerRepo makerRepo;

    public boolean maker_model_exist(Car car) {
        boolean b = true;
        b = b && maker_exist(car);
        b = b && model_exist(car);
        return b;
    }

    private boolean maker_exist(Car car) {
        return exist(car,
                      e -> Maker.from(e),
                      e -> makerRepo.findById(e.getID()),
                      e -> vechileAPI.GetAllMakes(),
                      e -> makerRepo.save(e),
                      e -> e.isExists());
    }

    private boolean model_exist(Car car) {
        return exist(car,
                      e -> Model.from(e),
                      e -> modelRepo.findById(e.getID()),
                      e -> vechileAPI.GetModels(e.getMake_Name()),
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
                              Function<Car, T> f_Car_2_T,
                              Function<T, T> f_redis_find_by_id,
                              Function<T, List<T>> f_rest_api,
                              Consumer<T> f_redis_save,
                              Function<T, Boolean> f_exists) {

        T m0 = f_Car_2_T.apply(car);
        T m1 = f_redis_find_by_id.apply(m0);

        if (m1 != null) {
            return f_exists.apply(m1);
        }

        List<T> l = f_rest_api.apply(m0);
        l.forEach(e -> f_redis_save.accept(e));
        m1 = f_redis_find_by_id.apply(m0);

        if (m1 != null) {
            return f_exists.apply(m1);
        }

        f_redis_save.accept(m0);
        return f_exists.apply(m0);
    }

}
