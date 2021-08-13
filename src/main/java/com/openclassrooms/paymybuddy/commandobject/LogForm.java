package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

@Data
public class LogForm {
    private String mail;
    private String password;

    public LogForm() {
    }
}
