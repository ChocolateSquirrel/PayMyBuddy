package com.openclassrooms.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {
		return userRepository.findByMail(usermail).orElseThrow(() -> new UsernameNotFoundException(usermail));
	}
}
