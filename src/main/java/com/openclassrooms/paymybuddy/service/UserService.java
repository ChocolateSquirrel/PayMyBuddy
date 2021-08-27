package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.AddConnectionForm;
import com.openclassrooms.paymybuddy.commandobject.CreateUserForm;
import com.openclassrooms.paymybuddy.model.PMBAccount;
import com.openclassrooms.paymybuddy.model.Role;
import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private final PMBAccountRepository pmbAccountRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PMBAccountRepository pmbAccountRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.pmbAccountRepository = pmbAccountRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}

	@Transactional
	public void createUser(CreateUserForm form) {
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		User user = new User();
		PMBAccount userPMBAccount = new PMBAccount();
		userPMBAccount.setBalance(0.0);
		userPMBAccount.setUser(user);
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setMail(form.getMail());
		user.setPassword(encodedPassword);
		user.setPmbAccount(userPMBAccount);
		user.getRoles().add(new Role("user"));
		User saveUser = userRepository.save(user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(saveUser, null, saveUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	public Optional<User> getConnectedUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByMail(authentication.getName());
	}

	public void connect2Users(User user1, AddConnectionForm addConnectionForm){
		Optional<User> user2Opt = userRepository.findByMail(addConnectionForm.getMail());
		if (!user2Opt.isPresent()){
			throw new UsernameNotFoundException(addConnectionForm.getMail());
		}
		User user2 = user2Opt.get();
		if (user1.getContacts().contains(user2)){
			throw new RuntimeException("You already have this contact.");
		}
		user1.getContacts().add(user2);
		userRepository.save(user1);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
		return userRepository.findByMail(userMail).orElseThrow(() -> new UsernameNotFoundException(userMail));
	}
}
