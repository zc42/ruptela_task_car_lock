package com.ruptela.car_repo.controller;

public class ControllerException extends Throwable {

    public ControllerException(String msg) {
        super(msg);
    }

    public ControllerException(RetCodes code) {
        super(code.toString());
    }
}
