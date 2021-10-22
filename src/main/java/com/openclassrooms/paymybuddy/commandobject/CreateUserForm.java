package com.openclassrooms.paymybuddy.commandobject;

import com.openclassrooms.paymybuddy.model.User;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CreateUserForm {
	
	private String firstName;
	private String lastName;

	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message="invalid mail")
	private String mail;

	private String password;
	private String confirm;
	
	public CreateUserForm() {
		super();
	}


}
