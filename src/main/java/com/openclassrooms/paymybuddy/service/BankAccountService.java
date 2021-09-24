package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.BankForm;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.BankAccountRepository;

import javax.transaction.Transactional;

@Service
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;

	public BankAccountService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	@Transactional
	public void createBankAccount(User user, BankForm form){
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUser(user);
		bankAccount.setIban(form.getIban());
		bankAccount.setBalance(form.getBalance());
		bankAccountRepository.save(bankAccount);
	}

	public BankAccount findByIban(String iban){
		return bankAccountRepository.findByIban(iban);
	}

	public void fund(BankAccount account, double amount){
		account.setBalance(account.getBalance() + amount);
	}

	public void withdraw(BankAccount account, double amount) throws Exception {
		double balance = account.getBalance();
		if (balance >= amount) account.setBalance(account.getBalance()-amount);
		else throw new Exception("Not enough on your bank account ! Balance: " + String.valueOf(balance));
	}
}
