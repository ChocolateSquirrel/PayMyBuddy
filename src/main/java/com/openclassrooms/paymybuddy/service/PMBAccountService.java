package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.PMBAccount;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;

@Service
public class PMBAccountService {

	private final PMBAccountRepository pmbAccountRepository;

	public PMBAccountService(PMBAccountRepository pmbAccountRepository) {
		this.pmbAccountRepository = pmbAccountRepository;
	}

	public void fund(PMBAccount account, double amount){
		account.setBalance(account.getBalance()+amount);
	}

	public void withdraw(PMBAccount account, double amount) throws Exception {
		double balance = account.getBalance();
		if (balance >= amount) account.setBalance(account.getBalance()-amount);
		else throw new Exception("Not enough on your PMB account ! Balance: " + String.valueOf(balance));
	}

	public void withdrawWithCommission(PMBAccount account, double amount) throws Exception {
		double newAmount = amount + (double) Math.round(0.05*amount*100)/100;
		try {
			withdraw(account, newAmount);
		} catch (Exception e) {
			throw new Exception("Not enough on your PMB account ! You need " + newAmount + " money (the application take 5% of the amount) and you only have " + account.getBalance() );
		}
	}
}
