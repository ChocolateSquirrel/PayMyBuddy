package com.openclassrooms.paymybuddy.commandobject;

import com.openclassrooms.paymybuddy.model.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateUserForm {

	@NotBlank(message = "firstName is mandatory")
	private String firstName;

	@NotBlank(message = "lastName is mandatory")
	private String lastName;

	@Email(regexp = "[\\w-]+@([\\w-]+\\.)+[\\w-]+", message="invalid mail")
	private String mail;

	private String password;
	private String confirm;
	
	public CreateUserForm() {
		super();
	}


}
