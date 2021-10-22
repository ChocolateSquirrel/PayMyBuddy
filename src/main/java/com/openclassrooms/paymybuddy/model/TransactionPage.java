package com.openclassrooms.paymybuddy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Getter
@Setter
public class TransactionPage {
    List<Transaction> transcationsList;


}
