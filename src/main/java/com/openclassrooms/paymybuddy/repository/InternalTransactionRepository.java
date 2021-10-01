package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalTransactionRepository extends CrudRepository<InternalTransaction, Integer> {

    @Query(value = "SELECT t FROM InternalTransaction t WHERE t.pmbAccount = :pmbAccount")
    public List<InternalTransaction> findByPmbAccount(@Param("pmbAccount") PMBAccount pmbAccount);

}
