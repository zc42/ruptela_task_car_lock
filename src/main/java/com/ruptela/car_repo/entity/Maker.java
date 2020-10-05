package com.ruptela.car_repo.entity;

import java.io.Serializable;
import java.util.Map;

public class Maker implements Serializable {

    private Integer Make_ID;
    private String Make_Name;
    private boolean exists;

    public Maker() {
    }

    public Maker(Integer Make_ID, String Make_Name, boolean exists) {
        this.Make_ID = Make_ID;
        this.Make_Name = Make_Name;
        this.exists = exists;
    }

    public static Maker from(Car car) {
        return new Maker(-1, car.getMake(), false);
    }

    public static Maker from(Map<String, Object> m) {
        return new Maker(
                (Integer) m.get("Make_ID"),
                (String) m.get("Make_Name"),
                true);
    }

    public Integer getMake_ID() {
        return Make_ID;
    }

    public void setMake_ID(Integer make_ID) {
        Make_ID = make_ID;
    }

    public String getMake_Name() {
        return Make_Name;
    }

    public void setMake_Name(String make_Name) {
        Make_Name = make_Name;
    }

    public String getID() {
        return getMake_Name();
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

}
