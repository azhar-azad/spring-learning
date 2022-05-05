package com.azad.simplebankapi.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.azad.simplebankapi.entities.Customer;
import com.azad.simplebankapi.entities.SecurityCustomer;
import com.azad.simplebankapi.repositories.CustomerRepository;

@Service
public class SimpleBankUserDetails implements UserDetailsManager {

	private CustomerRepository customerRepository;
	
	@Autowired
	public SimpleBankUserDetails(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(username);
		if (customer == null) {
			throw new UsernameNotFoundException("User details not found for the user " + username);
		}
		return new SecurityCustomer(customer);
	}

	@Override
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
