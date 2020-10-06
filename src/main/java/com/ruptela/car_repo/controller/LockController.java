package com.ruptela.car_repo.controller;

import com.ruptela.car_repo.controller.parts.ControllerException;
import com.ruptela.car_repo.controller.parts.RetCodes;
import com.ruptela.car_repo.service.CarLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "${resource.lock_controller_req_path}")
public class LockController {

    @Autowired
    private CarLockService carLockService;

    @GetMapping(path = "/lock_car")
    public @ResponseBody
    RetCodes lockCar(@RequestParam String vin,
                     @RequestParam boolean lock) throws ControllerException {
        return carLockService.lockCar(vin, lock);
    }

    @GetMapping(path = "/get_car_state")
    public @ResponseBody
    RetCodes getCarState(@RequestParam String vin) throws ControllerException {
        return carLockService.getCarState(vin);
    }

}
