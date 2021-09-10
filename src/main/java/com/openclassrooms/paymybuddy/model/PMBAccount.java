package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
			mappedBy = "pmbAccount",
			cascade = CascadeType.ALL
			)
	@Column(name = "int_debit_transactions")
	private List<InternalTransaction> intDebitTransactions = new ArrayList<>();
	
	
	@OneToMany(
			mappedBy = "debitAccount",
			cascade = CascadeType.ALL
			)
	@Column(name = "ext_debit_transactions")
	private List<ExternalTransaction> extDebitTransactions = new ArrayList<>();
	
	
	@OneToMany(
			mappedBy = "creditAccount",
			cascade = CascadeType.ALL
			)
	@Column(name = "ext_credit_transactions")
	private List<ExternalTransaction> extCreditTransactions = new ArrayList<>();
	
	public PMBAccount() {
		super();
	}

}
