package com.openclassrooms.paymybuddy.model;

import java.time.LocalDateTime;

public interface Transaction {

    LocalDateTime date = null;
    String type = "";

    public default LocalDateTime getDate(){
        return date;
    }

    public default String getType(){
        return type;
    }
}
