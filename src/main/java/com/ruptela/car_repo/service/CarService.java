package com.ruptela.car_repo.service;

import com.ruptela.car_repo.controller.parts.ControllerException;
import com.ruptela.car_repo.dao.CarRepository;
import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.redis.repos.RedisCarRepo;
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

    public Car create(String vin, String make, String model, String plate_nb) throws ControllerException {

        Car car = Car.from(make, model, vin, plate_nb);
        if (!vechiles.maker_model_exist(car)) {
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

    public Iterable<Car> list_cars() {
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
