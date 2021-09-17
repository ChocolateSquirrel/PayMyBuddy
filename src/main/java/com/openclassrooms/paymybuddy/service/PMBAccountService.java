package com.openclassrooms.paymybuddy.service;

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
		else throw new Exception("Not enough on your account ! Balance: " + String.valueOf(balance));
	}
}
