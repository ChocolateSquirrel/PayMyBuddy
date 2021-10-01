package com.openclassrooms.paymybuddy.model;

import java.time.LocalDate;

public interface Transaction {

    LocalDate date = null;

    public default LocalDate getDate(){
        return date;
    }
}
