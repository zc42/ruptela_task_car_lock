package com.ruptela.carLockRepo.controller.parts;

public class ControllerException extends RuntimeException {

    private RetCodes code;

    public ControllerException(String msg) {
        super(msg);
    }

    private ControllerException(RetCodes code) {
        super(code.toString());
        this.code = code;
    }

    public boolean equals(RetCodes v) {
        return code != null && code.equals(v);

    }

    public static ControllerException from(RetCodes retCodes) {
        return new ControllerException(retCodes);
    }
}
