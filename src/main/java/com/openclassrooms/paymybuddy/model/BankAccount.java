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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int bankAccountId;
	
	@Column(name = "iban")
	private String iban;

	@Column(name = "balance")
	private double balance;
	
	@OneToMany(
			mappedBy = "bankAccount"
			)
	@Column(name = "int_transactions")
	private List<InternalTransaction> intTransactions = new ArrayList<>();
	
	@ManyToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
			)
	private User user;

	
	public BankAccount() {
		super();
	}
	
	

}
