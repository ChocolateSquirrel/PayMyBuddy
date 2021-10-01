package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.AddConnectionForm;
import com.openclassrooms.paymybuddy.commandobject.CreateUserForm;
import com.openclassrooms.paymybuddy.exception.EntityNotFoundException;
import com.openclassrooms.paymybuddy.exception.ValidationException;
import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.ExternalTransactionRepository;
import com.openclassrooms.paymybuddy.repository.InternalTransactionRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.thymeleaf.util.StringUtils;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final BankAccountRepository bankAccountRepository;
	private final InternalTransactionRepository internalTransactionRepository;
	private final ExternalTransactionRepository externalTransactionRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, BankAccountRepository bankAccountRepository, InternalTransactionRepository internalTransactionRepository, ExternalTransactionRepository externalTransactionRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.bankAccountRepository = bankAccountRepository;
		this.internalTransactionRepository = internalTransactionRepository;
		this.externalTransactionRepository = externalTransactionRepository;
	}

	@Transactional
	public void createUser(CreateUserForm form) throws Exception {
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
		if (!form.getPassword().equals(form.getConfirm())){
			throw new ValidationException(UserService.class, "password", "password and confirmation must match");
		}
		User saveUser = userRepository.save(user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(saveUser, null, saveUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	@Transactional
	public Optional<User> getConnectedUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByMail(authentication.getName()).map(u -> {
			u.setBankAccounts(bankAccountRepository.findAllByUser(u));
			return u;
		});
	}

	public List<Transaction> getTransactions(User user){
		List<Transaction> transList = new ArrayList<>();
		PMBAccount userAccount = user.getPmbAccount();
		List<InternalTransaction> internTransList = internalTransactionRepository.findByPmbAccount(userAccount);
		List<ExternalTransaction> extTransCreditList = externalTransactionRepository.findByPmbAccountCredit(userAccount);
		List<ExternalTransaction> extTransDebitList = externalTransactionRepository.findByPmbAccountDebit(userAccount);
		List<Transaction> internList = internTransList.stream().map(t -> (Transaction) t).collect(Collectors.toList());
		List<Transaction> extCreList = extTransCreditList.stream().map(t -> (Transaction) t).collect(Collectors.toList());
		List<Transaction> extDebitList = extTransDebitList.stream().map(t -> (Transaction) t).collect(Collectors.toList());

		transList.addAll(internList);
		transList.addAll(extCreList);
		transList.addAll(extDebitList);
		Collections.sort(transList, (Transaction t1, Transaction t2) -> {
			return t1.getDate().getChronology().compareTo(t2.getDate().getChronology());
		});
		return transList;
	}

	@Transactional
	public void connect2Users(User user1, AddConnectionForm addConnectionForm) throws Exception {
		if (StringUtils.isEmpty(addConnectionForm.getMail())){
			throw new ValidationException(UserService.class, "connection", "you must enter an email address.");
		}
		Optional<User> user2Opt = userRepository.findByMail(addConnectionForm.getMail());
		if (!user2Opt.isPresent()){
			throw new EntityNotFoundException(User.class, addConnectionForm.getMail());
		}
		User user2 = user2Opt.get();
		if (user1.getContacts().contains(user2)){
			throw new ValidationException(UserService.class, "connection", "You already have this contact.");
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
