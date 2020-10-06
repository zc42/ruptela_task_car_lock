package com.ruptela.car_repo.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class Car implements Serializable {

    @Id
    private String vin;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(nullable = true)
    private String plateNb;
    @Column(nullable = false)
    private boolean locked = false;
    @Transient
    private boolean exists = true;

    public Car(String vin, String make, String model, String plateNb, boolean exists) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.plateNb = plateNb;
        this.exists = exists;
    }

    public static Car from(String make, String model, String vin, String plate_nb) {
        return new Car(vin, make, model, plate_nb, true);
    }

    public static Car from(String vin) {
        return new Car(vin, null, null, null, false);
    }

    public Car withExists(boolean b) {
        return new Car(vin, make, model, plateNb, b);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.vin);
        hash = 97 * hash + Objects.hashCode(this.make);
        hash = 97 * hash + Objects.hashCode(this.model);
        hash = 97 * hash + Objects.hashCode(this.plateNb);
        hash = 97 * hash + (this.locked ? 1 : 0);
        hash = 97 * hash + (this.exists ? 1 : 0);
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
        final Car other = (Car) obj;
        if (this.locked != other.locked) {
            return false;
        }
        if (this.exists != other.exists) {
            return false;
        }
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

    @Override
    public String toString() {
        return "Car{" + "vin=" + vin + ", make=" + make + ", model=" + model + ", plateNb=" + plateNb + ", locked=" + locked + ", exists=" + exists + '}';
    }


}
