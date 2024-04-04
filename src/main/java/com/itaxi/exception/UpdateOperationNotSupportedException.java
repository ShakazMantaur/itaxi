package com.itaxi.exception;

public class UpdateOperationNotSupportedException extends RuntimeException {
    public UpdateOperationNotSupportedException() {
        super("Saving product with given id is not supported.");
    }
}