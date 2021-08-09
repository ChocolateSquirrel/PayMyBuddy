package com.openclassrooms.paymybuddy.commandobject;

import lombok.Data;

@Data
public class CreationForm {
	
	private String firstName;
	private String lastName;
	private String mail;
	private String password;
	private String confirm;
	
	public CreationForm() {
		super();
	}

}
