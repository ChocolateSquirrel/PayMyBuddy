package com.openclassrooms.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	public Optional<User> findByMail(String mail);

}
