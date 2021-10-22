package com.openclassrooms.paymybuddy.commandobject;

import com.openclassrooms.paymybuddy.model.BankAccount;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class InternalTransactionForm {

    private String bankAccountIban;

    @Min(value = 0L, message="the value must be positive")
    private double amount;

    private String signe;

    public InternalTransactionForm() {
    }
}
