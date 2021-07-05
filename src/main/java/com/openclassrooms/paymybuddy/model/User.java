package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "utilisateur")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int userId;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "password")
	private String password;
	
	@ManyToMany
	@Column(name = "contacts")
	private List<User> contacts;
	
	@OneToMany(
			mappedBy = "utilisateur"
			)
	private List<BankAccount> bankAccounts = new ArrayList<>();
	
	@OneToOne(
			mappedBy = "utilisateur"
			)
	private PMBAccount pmbAccount;

	
	public User() {
		super();
	}
	
	

}
