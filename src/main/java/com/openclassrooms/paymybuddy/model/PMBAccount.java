package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "paymybuddy_account")
public class PMBAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int accountId;
	
	@Column(name = "number")
	private int accountNumber;
	
	@Column(name = "balance")
	private double balance;
	
	@OneToOne 
	private User utilisateur;
	
	@OneToMany(
			mappedBy = "pmbAccount"
			)
	@Column(name = "int_debit_transactions")
	private List<InternalTransaction> intDebitTransactions = new ArrayList<>();
	
	
	@OneToMany(
			mappedBy = "debitAccount"
			)
	@Column(name = "ext_debit_transactions")
	private List<ExternalTransaction> extDebitTransactions = new ArrayList<>();
	
	
	@OneToMany(
			mappedBy = "creditAccount"
			)
	@Column(name = "ext_credit_transactions")
	private List<ExternalTransaction> extCreditTransactions = new ArrayList<>();
	
	public PMBAccount() {
		super();
	}

}
