package com.openclassrooms.paymybuddy.commandobject;

import com.openclassrooms.paymybuddy.model.User;
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
