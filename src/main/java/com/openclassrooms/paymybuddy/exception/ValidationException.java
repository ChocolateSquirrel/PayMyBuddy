package com.openclassrooms.paymybuddy.exception;

public class ValidationException extends PMBException {

    private static final String MESSAGE = "%s : %s invalid. %s";

    public ValidationException(Class<?> classes, String id, String message) {
        super(String.format(MESSAGE, classes.getSimpleName(), id , message));
    }
}
