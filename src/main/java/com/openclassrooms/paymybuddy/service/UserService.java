package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.commandobject.AddConnectionForm;
import com.openclassrooms.paymybuddy.commandobject.CreateUserForm;
import com.openclassrooms.paymybuddy.exception.EntityNotFoundException;
import com.openclassrooms.paymybuddy.exception.ValidationException;
import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.thymeleaf.util.StringUtils;


import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final BankAccountRepository bankAccountRepository;
	private final InternalTransactionRepository internalTransactionRepository;
	private final ExternalTransactionRepository externalTransactionRepository;
	private final TransactionRepository transactionRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, BankAccountRepository bankAccountRepository, InternalTransactionRepository internalTransactionRepository, ExternalTransactionRepository externalTransactionRepository, TransactionRepository transactionRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.bankAccountRepository = bankAccountRepository;
		this.internalTransactionRepository = internalTransactionRepository;
		this.externalTransactionRepository = externalTransactionRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
		return userRepository.findByMail(userMail).orElseThrow(() -> new UsernameNotFoundException(userMail));
	}

	@Transactional
	public void createUser(CreateUserForm form) throws Exception {
		String encodedPassword = passwordEncoder.encode(form.getPassword());
		// Create new user with all parameters recovered from the form AND create a new PMBAccount
		User user = new User();
		PMBAccount userPMBAccount = new PMBAccount();
		userPMBAccount.setBalance(0.0);
		userPMBAccount.setUser(user);
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setMail(form.getMail());
		user.setPassword(encodedPassword);
		user.setPmbAccount(userPMBAccount);
		// For the prototype, each user created has user role but it will be possible to have more role like admin...
		user.getRoles().add(new Role("user"));
		// Verify password and confirmation (need to be the same)
		if (!form.getPassword().equals(form.getConfirm())){
			throw new ValidationException(UserService.class, "password", "password and confirmation must match");
		}
		// Save User and generate a token added to the security context
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

		PMBAccount userAccount = user.getPmbAccount();
/*		List<InternalTransaction> internTransList = internalTransactionRepository.findByPmbAccount(userAccount);
		List<ExternalTransaction> extTransCreditList = externalTransactionRepository.findByPmbAccountCredit(userAccount);
		List<ExternalTransaction> extTransDebitList = externalTransactionRepository.findByPmbAccountDebit(userAccount);
		Stream<Transaction> internList = internTransList.stream().map(Function.identity());
		Stream<Transaction> extCreList = extTransCreditList.stream().map(Function.identity());
		Stream<Transaction> extDebitList = extTransDebitList.stream().map(Function.identity());*/

		Pageable pageWithThreeTransactions = PageRequest.of(0, 3, Sort.by("date").ascending());
		List<Transaction> allTransactions = transactionRepository.findAllByPMBAccount(userAccount, pageWithThreeTransactions);
		return allTransactions;

/*		return Stream.concat(internList, Stream.concat(extCreList, extDebitList))
				.sorted(Comparator.comparing(Transaction::getDate))
				.collect(Collectors.toList());*/
	}

	@Transactional
	public void connect2Users(User user1, AddConnectionForm addConnectionForm) {
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


}
