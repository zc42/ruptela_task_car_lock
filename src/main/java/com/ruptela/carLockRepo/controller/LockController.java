package com.ruptela.carLockRepo.controller;

import com.ruptela.carLockRepo.entity.LockCarDTO;
import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.controller.parts.RetCodes;
import com.ruptela.carLockRepo.service.CarLockService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${resource.lock_controller_req_path}")
public class LockController {

    @Autowired
    private CarLockService carLockService;

    @PutMapping(path = "/lock_car")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RetCodes lockCar(@Valid @RequestBody LockCarDTO dto) throws ControllerException {
        return carLockService.lockCar(dto.getVin(), dto.getLock());
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/get_car_state/{vin}")
    public @ResponseBody
    RetCodes getCarState(@PathVariable(name = "vin") String vin) throws ControllerException {
        return carLockService.getCarState(vin);
    }

}
