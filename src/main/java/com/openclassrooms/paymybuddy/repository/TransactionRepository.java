package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.PMBAccount;
import com.openclassrooms.paymybuddy.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    @Query(value = "SELECT t FROM ExternalTransaction t WHERE t.creditAccount = :pmbAccount OR t.debitAccount = :pmbAccount" +
            "UNION" +
            "SELECT t FROM InternalTransaction t WHERE t.pmbAccount = :pmbAccount")
    List<Transaction> findAllByPMBAccount(PMBAccount pmbAccount, Pageable pageable);
}
