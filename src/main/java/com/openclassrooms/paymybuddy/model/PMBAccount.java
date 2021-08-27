package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

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
	private String accountNumber = UUID.randomUUID().toString();
	
	@Column(name = "balance")
	private double balance;
	
	@OneToOne (
			cascade = CascadeType.ALL
	)
	private User user;
	
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
