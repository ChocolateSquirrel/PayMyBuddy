package com.openclassrooms.paymybuddy.exception;

public class EntityNotFoundException extends PMBException {

    private static final String MESSAGE = "Entity %s was not found for identifier %s";

    public EntityNotFoundException(Class<?> classes, String id){
        super(String.format(MESSAGE, classes.getSimpleName(), id));
    }
}
