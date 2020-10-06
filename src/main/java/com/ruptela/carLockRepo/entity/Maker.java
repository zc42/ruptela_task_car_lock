package com.ruptela.carLockRepo.entity;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Maker implements Serializable {

    private Integer makeId;
    private String makeName;
    private boolean exists;

    public Maker(Integer makeId, String makeName, boolean exists) {
        this.makeId = makeId;
        this.makeName = makeName;
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

}
