package com.ruptela.car_repo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "cars")
public class Car implements Serializable {

    @Id
    private String vin;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(nullable = true)
    private String plate_nb;
    @Column(nullable = false)
    private boolean locked = false;
    @Transient
    private boolean exists=true;

    public Car() {
    }

    public Car(String vin, String make, String model, String plate_nb, boolean exists) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.plate_nb = plate_nb;
        this.exists = exists;
    }

    public static Car from(String make, String model, String vin, String plate_nb) {
        return new Car(vin, make, model, plate_nb, true);
    }

    public static Car from(String vin) {
        return new Car(vin, null, null, null, false);
    }

    public Car with_exists(boolean b) {
        return new Car(vin, make, model, plate_nb, b);
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate_nb() {
        return plate_nb;
    }

    public void setPlate_nb(String plate_nb) {
        this.plate_nb = plate_nb;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    @Override
    public String toString() {
        return "Car{" + "vin=" + vin + ", make=" + make + ", model=" + model + ", plate_nb=" + plate_nb + ", locked=" + locked + ", exists=" + exists + '}';
    }

}
