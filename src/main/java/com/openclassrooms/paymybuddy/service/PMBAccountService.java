package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.BalanceException;
import com.openclassrooms.paymybuddy.model.PMBAccount;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import org.springframework.stereotype.Service;

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
		else throw new BalanceException(PMBAccount.class, account.getAccountNumber(), amount, account.getBalance());
	}

	public void withdrawWithCommission(PMBAccount account, double amount) throws Exception {
		double newAmount = amount + (double) Math.round(0.05*amount*100)/100;
		try {
			withdraw(account, newAmount);
		} catch (Exception e) {
			throw new BalanceException(PMBAccount.class, account.getAccountNumber(), newAmount, account.getBalance());
		}
	}
}
