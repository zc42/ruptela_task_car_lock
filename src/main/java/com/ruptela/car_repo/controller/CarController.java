package com.ruptela.car_repo.controller;

import com.ruptela.car_repo.controller.parts.ControllerException;
import com.ruptela.car_repo.entity.Car;
import com.ruptela.car_repo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "${resource.car_controller_req_path}")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(path = "/create_car")
    public @ResponseBody
    Car createCar(@RequestParam String vin,
                  @RequestParam String make,
                  @RequestParam String model,
                  @RequestParam(required = false) String plateNb) throws ControllerException {
        return carService.createCar(vin, make, model, plateNb);
    }

    @GetMapping(path = "/list_cars")
    public @ResponseBody
    Iterable<Car> listCars() {
        return carService.listCars();
    }

}
