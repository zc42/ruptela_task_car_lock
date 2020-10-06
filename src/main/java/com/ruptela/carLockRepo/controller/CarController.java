package com.ruptela.carLockRepo.controller;

import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.entity.Car;
import com.ruptela.carLockRepo.entity.CarDTO;
import com.ruptela.carLockRepo.service.CarService;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

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

    @GetMapping(path = "/list_cars")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<CarDTO> listCars() {
        Iterable<Car> it = carService.listCars();
        return StreamSupport.stream(it.spliterator(), false)
                .map(e -> CarDTO.from(e))
                .collect(toList());
//        return Streams.carService.listCars();
    }

}
