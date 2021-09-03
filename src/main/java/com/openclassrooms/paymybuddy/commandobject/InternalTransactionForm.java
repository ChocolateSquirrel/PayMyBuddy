package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

@Data
public class InternalTransactionForm {

    private double amount;
    private String signe;

    public InternalTransactionForm() {
    }
}
