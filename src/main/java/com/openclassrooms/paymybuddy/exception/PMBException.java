package com.openclassrooms.paymybuddy.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PMBException extends RuntimeException {

    public PMBException(String message) {
        super(message);
        log.error(this.getClass().getSimpleName() + ":" + message);
    }
}
