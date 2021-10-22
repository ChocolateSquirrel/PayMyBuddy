package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BankForm {

    private String iban;

    @Min(value = 0L, message="the value must be positive")
    private double balance;

    public BankForm() {
    }
}
