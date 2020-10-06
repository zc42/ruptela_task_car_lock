package com.ruptela.carLockRepo.service;

import com.ruptela.carLockRepo.entity.CarDTO;
import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.dao.CarRepository;
import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.redis.repos.RedisCarRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private VechileService vechiles;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RedisCarRepo redis;

    public Car createCar(CarDTO v) throws ControllerException {

        Car car = Car.from(v);
        if (!vechiles.makerModelExist(car)) {
            throw new ControllerException("maker/model does not exist: "
                                          + car.getMake()
                                          + " "
                                          + car.getModel());
        }

        Optional<Car> car0 = carRepository.findById(car.getVin());
        if (car0.isPresent()) {
            throw new ControllerException("car allready exists: " + car0.get());
        }

        carRepository.save(car);
        redis.save(car);

        return car;
    }

    public Iterable<Car> listCars() {
        return carRepository.findAll();
    }

    public Car findById(String id) {
        Optional<Car> v = carRepository.findById(id);
        return v.isPresent() ? v.get() : null;
    }

    public Car save(Car v) {
        return carRepository.save(v);
    }

}
