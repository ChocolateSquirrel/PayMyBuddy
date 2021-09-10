package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

@Data
public class ExternalTransactionForm {

    private double amount;
    private String mailOfCrediter;
    private String description;

    public ExternalTransactionForm() {
    }
}
