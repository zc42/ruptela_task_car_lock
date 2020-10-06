package com.ruptela.carLockRepo.entity;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class CarDTO {

    @NotNull
    private String vin;
    @NotNull
    private String make;
    @NotNull
    private String model;
    private String plateNb;

    static public CarDTO from(String vin,
                              String make,
                              String model,
                              String plateNb) {
        CarDTO o = new CarDTO();
        o.vin = vin;
        o.make = make;
        o.model = model;
        o.plateNb = plateNb;
        return o;
    }
}
