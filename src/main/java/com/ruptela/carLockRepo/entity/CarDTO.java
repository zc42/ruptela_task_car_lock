package com.ruptela.carLockRepo.entity;

import java.util.Objects;
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
    private Boolean locked;

    static public CarDTO from(String vin,
                              String make,
                              String model,
                              String plateNb) {
        CarDTO r = new CarDTO();
        r.vin = vin;
        r.make = make;
        r.model = model;
        r.plateNb = plateNb;
        return r;
    }

    static public CarDTO from(String vin,
                              String make,
                              String model,
                              String plateNb,
                              boolean locked) {
        CarDTO r = new CarDTO();
        r.vin = vin;
        r.make = make;
        r.model = model;
        r.plateNb = plateNb;
        r.locked = locked;
        return r;
    }

    public static CarDTO from(Car car) {
        CarDTO r = new CarDTO();
        r.vin = car.getVin();
        r.make = car.getMake();
        r.model = car.getModel();
        r.plateNb = car.getPlateNb();
        r.locked = car.isLocked();
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarDTO carDTO = (CarDTO) o;
        return Objects.equals(vin, carDTO.vin)
               && Objects.equals(make, carDTO.make)
               && Objects.equals(model, carDTO.model)
               && Objects.equals(plateNb, carDTO.plateNb)
               && Objects.equals(locked, carDTO.locked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, make, model, plateNb, locked);
    }

    @Override
    public String toString() {
        return "CarDTO{"
               + "vin='" + vin + '\''
               + ", make='" + make + '\''
               + ", model='" + model + '\''
               + ", plateNb='" + plateNb + '\''
               + ", locked=" + locked
               + '}';
    }
}
