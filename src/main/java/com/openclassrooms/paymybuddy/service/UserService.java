package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.CreationForm;
import com.openclassrooms.paymybuddy.commandobject.LogForm;
import com.openclassrooms.paymybuddy.model.PMBAccount;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

import javax.transaction.Transactional;
import java.util.Optional;

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

	public User logUser(LogForm form) throws IllegalAccessException {
		Optional<User> userLog = userRepository.findByMail(form.getMail());
		if (userLog.isPresent()){
			if (userLog.get().getPassword().equals(form.getPassword()))
				return userLog.get();
			else
				throw new IllegalAccessException("Wrong password !!!");
		}
		else
			throw new IllegalAccessException("No User for this mail address");
	}


	public void createUser(CreationForm form) {
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		User user = new User();
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setMail(form.getMail());
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
		return userRepository.findByMail(userMail).orElseThrow(() -> new UsernameNotFoundException(userMail));
	}
}
