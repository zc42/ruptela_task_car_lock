package com.ruptela.carLockRepo.service;

import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.controller.parts.RetCodes;
import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.kafka.producer.KafkaProducer;
import com.ruptela.carLockRepo.redis.repos.RedisCarRepo;
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
    else persistent db find e
    if found
        lock/unlock
        redis save e
        return state
    else
        redis save e with state not exist
     */
    public RetCodes lockCar(String vin, boolean lock) throws ControllerException {
        Car car = redis.findById(vin);
        return car != null
               ? lockCarRedis(car, lock)
               : lockCarPersist(vin, lock);
    }

    private RetCodes lockCarRedis(Car car, boolean lock) throws ControllerException {
        if (!car.isExists()) {
            throw ControllerException.from(RetCodes.err_no_car_found);
        }
        if (lock == car.isLocked()) {
            throw ControllerException.from(RetCodes.car_is_allready_in_state(lock));
        }

        car.setLocked(lock);
        redis.save(car);
        kafkaProducer.send(LockCarDTO.from(car.getVin(), lock));
        return RetCodes.ok;
    }

    private RetCodes lockCarPersist(String vin, boolean lock) throws ControllerException {
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

    public RetCodes getCarState(String vin) throws ControllerException {
        Car car = redis.findById(vin);
        return car != null
               ? getCarStateFromRedis(car)
               : getCarStateFromPersist(vin);
    }

    private RetCodes getCarStateFromRedis(Car car) throws ControllerException {
        if (car.isExists()) {
            return RetCodes.car_state_is(car.isLocked());
        }
        throw ControllerException.from(RetCodes.err_no_car_found);
    }

    private RetCodes getCarStateFromPersist(String vin) throws ControllerException {
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
