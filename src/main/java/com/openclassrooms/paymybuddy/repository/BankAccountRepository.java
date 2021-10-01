package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.BankAccount;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {

    BankAccount findByIban(String iban);
    List<BankAccount> findAllByUser(User user);

}
