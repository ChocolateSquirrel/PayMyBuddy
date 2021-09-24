package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

@Data
public class BankForm {
    private String iban;
    private double balance;

    public BankForm() {
    }
}
