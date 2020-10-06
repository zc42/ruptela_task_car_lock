package com.ruptela.carLockRepo.controller;

import com.ruptela.carLockRepo.entity.CarDTO;
import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.service.CarService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${resource.car_controller_req_path}")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping(path = "/create_car")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Car createCar(@Valid @RequestBody CarDTO v) throws ControllerException {
        return carService.createCar(v);
    }

    @GetMapping(path = "/create_car_test/{o1}/{o2}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Object createCar_test(@PathVariable(name = "o1") Object o1,
                          @PathVariable(name = "o2", required = false) Object o2) throws ControllerException {
        System.out.println("o1: " + o1);
        System.out.println("o2: " + o2);

        return o1;
    }

    @GetMapping(path = "/list_cars")
    public @ResponseBody
    Iterable<Car> listCars() {
        return carService.listCars();
    }

}
