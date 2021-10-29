package com.openclassrooms.paymybuddy.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaRepository<User, Integer> {

	public Optional<User> findByMail(@Param("mail") String mail);

}
