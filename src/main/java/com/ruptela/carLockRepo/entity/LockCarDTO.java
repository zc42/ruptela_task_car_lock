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
        LockCarDTO r = new LockCarDTO();
        r.vin = vin;
        r.lock = lock;
        return r;
    }

    @Override
    public String toString() {
        return "LockCarDTO{" + "vin=" + vin + ", lock=" + lock + '}';
    }

}
