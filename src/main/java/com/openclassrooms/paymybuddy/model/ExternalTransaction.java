package com.openclassrooms.paymybuddy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ext_transaction")
public class ExternalTransaction extends Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int extTransId;
	
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "debit_account")
	private PMBAccount debitAccount;
	
	@ManyToOne
	@JoinColumn(name = "credit_account")
	private PMBAccount creditAccount;
	
	@Column(name = "amount")
	private double amount;

	public ExternalTransaction() {
		super();
	}

}
