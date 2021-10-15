package com.openclassrooms.paymybuddy.model;

import java.time.LocalDate;

public interface Transaction {

    LocalDate date = null;
    String type = "";

    public default LocalDate getDate(){
        return date;
    }

    public default String getType(){
        return type;
    }
}
