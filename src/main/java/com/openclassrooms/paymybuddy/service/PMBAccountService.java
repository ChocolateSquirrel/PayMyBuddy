package com.openclassrooms.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.PMBAccountRepository;

@Service
public class PMBAccountService {

	@Autowired
	private PMBAccountRepository pmbAccountRepository;
}
