package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.CreationForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

import javax.transaction.Transactional;

@Log4j2
@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public void createUser(CreationForm form) {
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		User user = new User();
		user.setFirstName(form.getFirstName());
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {
		return userRepository.findByMail(usermail).orElseThrow(() -> new UsernameNotFoundException(usermail));
	}
}
