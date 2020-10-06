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

    public static CarDTO from(Car e) {
        CarDTO o = new CarDTO();
        o.vin = e.getVin();
        o.make = e.getMake();
        o.model = e.getModel();
        o.plateNb = e.getPlateNb();
        return o;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.vin);
        hash = 41 * hash + Objects.hashCode(this.make);
        hash = 41 * hash + Objects.hashCode(this.model);
        hash = 41 * hash + Objects.hashCode(this.plateNb);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarDTO other = (CarDTO) obj;
        if (!Objects.equals(this.vin, other.vin)) {
            return false;
        }
        if (!Objects.equals(this.make, other.make)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.plateNb, other.plateNb)) {
            return false;
        }
        return true;
    }

}
