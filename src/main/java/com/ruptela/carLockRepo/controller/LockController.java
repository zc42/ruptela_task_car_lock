package com.ruptela.carLockRepo.controller;

import com.ruptela.carLockRepo.controller.parts.ControllerException;
import com.ruptela.carLockRepo.controller.parts.RetCodes;
import com.ruptela.carLockRepo.entity.LockCarDTO;
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

    @GetMapping(path = "/get_car_state/{vin}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RetCodes getCarState(@PathVariable(name = "vin") String vin) throws ControllerException {
        return carLockService.getCarState(vin);
    }

}
