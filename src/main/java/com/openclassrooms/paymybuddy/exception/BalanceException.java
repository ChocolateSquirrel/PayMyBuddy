package com.openclassrooms.paymybuddy.exception;

public class BalanceException extends PMBException {

    private static final String MESSAGE = "%s : %s : not enough money. Need %s euros and have %s euros.";

    public BalanceException(Class<?> classes, String id, double needAmount, double haveAmount){
        super(String.format(MESSAGE, classes.getSimpleName(), id, needAmount ,haveAmount));
    }

    public BalanceException(String message){
        super(message);
    }
}
