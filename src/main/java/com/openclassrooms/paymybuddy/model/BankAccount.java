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

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int bankAccountId;
	
	@Column(name = "iban")
	private int iban;
	
	@OneToMany(
			mappedBy = "bankAccount"
			)
	@Column(name = "int_credit_transactions")
	private List<InternalTransaction> intCreditTransactions = new ArrayList<>();
	
	@ManyToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
			)
	private User utilisateur;

	
	public BankAccount() {
		super();
	}
	
	

}
