package com.ruptela.car_repo.service;

import com.ruptela.car_repo.controller.ControllerException;
import com.ruptela.car_repo.controller.RetCodes;
import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.kafka.producer.KafkaProducer;
import com.ruptela.car_repo.redis.RedisCarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarLockService {

    @Autowired
    private RedisCarRepo redis;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private CarService carService;

    /*
    redis find e
    if found
        if state not exist return err
        else lock/unlock
        kafka send msg -> persist db update e
        return state
    else persistant db find e
    if found
        lock/unlock
        redis save e
        return state
    else
        redis save e with state not exist
     */
    public RetCodes lock_car(String vin, boolean lock) throws ControllerException {
        Car car = redis.findById(vin);
        return car != null
               ? lock_car_redis(car, lock, vin)
               : lock_car_persist(vin, lock);
    }

    private RetCodes lock_car_redis(Car car, boolean lock, String vin) throws ControllerException {
        if (!car.isExists()) {
            throw ControllerException.from(RetCodes.err_no_car_found);
        }
        if (lock == car.isLocked()) {
            throw ControllerException.from(RetCodes.car_is_allready_in_state(lock));
        }

        car.setLocked(lock);
        redis.save(car);
        kafkaProducer.sendMessage(vin, lock);
        return RetCodes.ok;
    }

    private RetCodes lock_car_persist(String vin, boolean lock) throws ControllerException {
        Car car = carService.findById(vin);
        if (car != null) {
            if (lock == car.isLocked()) {
                throw ControllerException.from(RetCodes.car_is_allready_in_state(lock));
            }
            car.setLocked(lock);
            redis.save(car);
            carService.save(car);
            return RetCodes.ok;
        } else {
            redis.save(Car.from(vin));
            throw ControllerException.from(RetCodes.err_no_car_found);
        }
    }

    public RetCodes get_car_state(String vin) throws ControllerException {
        Car car = redis.findById(vin);
        return car != null
               ? car_state_redis(car)
               : car_state_persist(vin);
    }

    private RetCodes car_state_redis(Car car) throws ControllerException {
        if (car.isExists()) {
            return RetCodes.car_state_is(car.isLocked());
        }
        throw ControllerException.from(RetCodes.err_no_car_found);
    }

    private RetCodes car_state_persist(String vin) throws ControllerException {
        Car car = carService.findById(vin);
        if (car != null) {
            redis.save(car);
            return RetCodes.car_state_is(car.isLocked());
        } else {
            redis.save(Car.from(vin));
            throw ControllerException.from(RetCodes.err_no_car_found);
        }
    }
}
