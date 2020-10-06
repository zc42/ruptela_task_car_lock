package com.ruptela.carLockRepo.entity;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Model implements Serializable {

    private Integer modelId;
    private String makeName;
    private String modelName;
    private boolean exists = true;

    public Model(Integer modelId, String makeName, String modelName, boolean exist) {
        this.modelId = modelId;
        this.makeName = makeName;
        this.modelName = modelName;
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

    public String getId() {
        return makeName + "_" + modelName;
    }

}
