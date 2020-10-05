package com.ruptela.car_repo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ControllerErrorAdvice {

    @ResponseBody
    @ExceptionHandler(ControllerException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String employeeNotFoundHandler(ControllerException ex) {
        return ex.getMessage();
    }
}
