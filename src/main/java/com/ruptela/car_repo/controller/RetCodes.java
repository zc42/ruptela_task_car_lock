package com.ruptela.car_repo.controller;

public enum RetCodes {
    err_no_car_found,
    err_car_is_allready_locked,
    err_car_is_allready_unlocked,
    ok,
    car_is_locked,
    car_is_unlocked;

    public static RetCodes car_is_allready_in_state(boolean v) {
        return v
               ? err_car_is_allready_locked
               : err_car_is_allready_unlocked;
    }

    public static RetCodes car_state_is(boolean v) {
        return v
               ? car_is_locked
               : car_is_unlocked;
    }
}
