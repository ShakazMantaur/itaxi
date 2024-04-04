package com.itaxi.controller;

import com.itaxi.exception.UpdateOperationNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(UpdateOperationNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String updateNotSupported(UpdateOperationNotSupportedException ex) {
        return ex.getMessage();
    }


}