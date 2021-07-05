package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.ExternalTransaction;

@Repository
public interface ExternalTransactionRepository extends CrudRepository<ExternalTransaction, Integer> {

}
