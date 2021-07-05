package com.openclassrooms.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.ExternalTransactionRepository;

@Service
public class ExternalTransactionService {

	@Autowired
	private ExternalTransactionRepository externalTransactionRepository;
}
