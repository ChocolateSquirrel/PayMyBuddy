package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int userId;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;


	@Column(name = "mail")
	private String mail;
	
	@Column(name = "password")
	private String password;
	
	@ManyToMany(
			cascade = CascadeType.ALL
	)
	@Column(name = "contacts")
	private List<User> contacts = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "user"
			)
	private List<BankAccount> bankAccounts = new ArrayList<>();
	
	@OneToOne(
			mappedBy = "user",
			cascade = CascadeType.ALL
			)
	private PMBAccount pmbAccount;

	@ManyToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
	)
	private List<Role> roles = new ArrayList<>();
	
	public List<Role> getRoles(){
		return this.roles;
	}

	
	public User() {
		super();
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}


	@Override
	public String getUsername() {
		return this.mail;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}
	
	

}
