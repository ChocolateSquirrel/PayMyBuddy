package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.PMBAccount;

import java.util.Optional;

@Repository
public interface PMBAccountRepository extends CrudRepository<PMBAccount, Integer> {

}
