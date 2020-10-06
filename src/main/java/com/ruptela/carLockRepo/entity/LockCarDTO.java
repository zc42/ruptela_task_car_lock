package com.ruptela.carLockRepo.entity;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class LockCarDTO {

    @NotNull
    private String vin;
    @NotNull
    private Boolean lock;

    static public LockCarDTO from(String vin, Boolean lock) {
        LockCarDTO o = new LockCarDTO();
        o.vin = vin;
        o.lock = lock;
        return o;
    }

    @Override
    public String toString() {
        return "LockCarDTO{" + "vin=" + vin + ", lock=" + lock + '}';
    }

}
