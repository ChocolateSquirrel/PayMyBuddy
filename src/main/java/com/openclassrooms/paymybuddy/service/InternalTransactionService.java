package com.openclassrooms.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.InternalTransactionRepository;

@Service
public class InternalTransactionService {

	@Autowired
	private InternalTransactionRepository internalTransactionRepository;
}
