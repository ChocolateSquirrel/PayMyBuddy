package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExternalTransactionRepository extends CrudRepository<ExternalTransaction, Integer> {

    /**
     * Find all external transactions where the PMBAccount receive money (credit on PMBAccount)
     * @param pmbAccount : account which receive money
     * @return list of Internal transaction (only when PMBAccount receive money)
     */
    @Query(value = "SELECT t FROM ExternalTransaction t WHERE t.creditAccount = :pmbAccount")
    public List<ExternalTransaction> findByCreditAccount(@Param("pmbAccount") PMBAccount pmbAccount);

    /**
     * Find all external transactions where the PMBAccount give money (debit on PMBAccount)
     * @param pmbAccount : account which give money
     * @return list of Internal transaction (only when PMBAccount give money)
     */
    @Query(value = "SELECT t FROM ExternalTransaction t WHERE t.debitAccount = :pmbAccount")
    public List<ExternalTransaction> findByPmbAccountDebit(@Param("pmbAccount") PMBAccount pmbAccount);
}
