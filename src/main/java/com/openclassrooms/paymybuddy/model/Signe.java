package com.openclassrooms.paymybuddy.model;

public enum Signe {
	PLUS("+"), MINUS("-");
	
	String symbol;
	
	Signe(String symbol) {
		this.symbol = symbol;
	}

}
