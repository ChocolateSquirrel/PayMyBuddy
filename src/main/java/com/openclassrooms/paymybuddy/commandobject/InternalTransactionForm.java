package com.openclassrooms.paymybuddy.commandobject;

import com.openclassrooms.paymybuddy.model.BankAccount;
import lombok.Data;

@Data
public class InternalTransactionForm {

    private String bankAccountIban;
    private double amount;
    private String signe;

    public InternalTransactionForm() {
    }
}
