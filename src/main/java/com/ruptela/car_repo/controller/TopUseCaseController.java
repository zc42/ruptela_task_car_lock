package com.ruptela.car_repo.controller;

import com.ruptela.car_repo.controller.parts.ControllerException;
import com.ruptela.car_repo.controller.parts.RetCodes;
import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.service.CarLockService;
import com.ruptela.car_repo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "${resource.controller_req_path}")
public class TopUseCaseController {

    @Autowired
    private CarService carService;
    @Autowired
    private CarLockService carLockService;

    @GetMapping(path = "/create_car")
    public @ResponseBody
    Car createCar(@RequestParam String vin,
                     @RequestParam String make,
                     @RequestParam String model,
                     @RequestParam(required = false) String plate_nb) throws ControllerException {
        return carService.create(vin, make, model, plate_nb);
    }

    @GetMapping(path = "/list_cars")
    public @ResponseBody
    Iterable<Car> listCars() {
        return carService.list_cars();
    }

    @GetMapping(path = "/lock_car")
    public @ResponseBody
    RetCodes lockCar(@RequestParam String vin,
                     @RequestParam boolean lock) throws ControllerException {
        return carLockService.lock_car(vin, lock);
    }

    @GetMapping(path = "/get_car_state")
    public @ResponseBody
    RetCodes get_car_state(@RequestParam String vin) throws ControllerException {
        return carLockService.get_car_state(vin);
    }

}
