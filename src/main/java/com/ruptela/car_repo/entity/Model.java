package com.ruptela.car_repo.entity;

import java.io.Serializable;
import java.util.Map;

//@Entity(name = "models")
//@Table(indexes = {
//    @Index(name = "indx__make_id_model_name", columnList = "Make_Name, Model_Name", unique = true)})
public class Model implements Serializable {

//    @Id
    private Integer Model_ID;
//    @Column(nullable = false)
    private String Make_Name;
//    @Column(nullable = false)
    private String Model_Name;
//    @Transient
    private boolean exists = true;

    public Model() {
    }

    public Model(Integer model_ID, String make_NAME, String model_Name, boolean exist) {
        this.Model_ID = model_ID;
        this.Make_Name = make_NAME;
        this.Model_Name = model_Name;
        this.exists = exist;
    }

    public static Model from(Map<String, Object> m) {
        return new Model((Integer) m.get("Model_ID"),
                         (String) m.get("Make_Name"),
                         (String) m.get("Model_Name"),
                         true);
    }

    public static Model from(String maker, String model) {
        return new Model(-1,
                         maker,
                         model,
                         false);
    }

    public static Model from(Car car) {
        return new Model(-1,
                         car.getMake(),
                         car.getModel(),
                         false);
    }

    public Integer getModel_ID() {
        return Model_ID;
    }

    public void setModel_ID(Integer model_ID) {
        Model_ID = model_ID;
    }

    public String getMake_Name() {
        return Make_Name;
    }

    public void setMake_Name(String make_Name) {
        Make_Name = make_Name;
    }

    public String getModel_Name() {
        return Model_Name;
    }

    public void setModel_Name(String model_Name) {
        Model_Name = model_Name;
    }

    public String ID() {
        return getMake_Name() + "_" + getModel_Name();
    }

    public String getID() {
        return ID();
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

}
