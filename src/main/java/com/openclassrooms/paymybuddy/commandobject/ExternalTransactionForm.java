package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ExternalTransactionForm {

    @Min(value = 0L, message="the value must be positive")
    private double amount;
    private String mailOfCrediter;
    private String description;

    public ExternalTransactionForm() {
    }
}
