package com.openclassrooms.paymybuddy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "internal_transaction")
public class InternalTransaction implements Transaction{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int intTransId;
	
	@Column(name = "local_date")
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "bank_account")
	private BankAccount bankAccount;
	
	@ManyToOne
	@JoinColumn(name = "pmb_account")
	private PMBAccount pmbAccount;
	
	@Column(name = "amount")
	private double amount;

	@Column(name = "signe")
	private Signe signe;

	private String type = "int";

}
