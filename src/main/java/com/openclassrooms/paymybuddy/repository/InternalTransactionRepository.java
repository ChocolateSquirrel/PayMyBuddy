package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.InternalTransaction;

@Repository
public interface InternalTransactionRepository extends CrudRepository<InternalTransaction, Integer> {

}
