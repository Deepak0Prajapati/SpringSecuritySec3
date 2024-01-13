package com.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.model.Customer;
import com.security.repository.CustomerRepository;


@Service
public class EazyBankUserDetails implements UserDetailsService{
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String userName,password=null;
		List<GrantedAuthority> authorities=null;
		
		List<Customer>  customer=this.customerRepository.findByEmail(username);
		if(customer.size()==0) {
			throw new UsernameNotFoundException("user details not found for the user:"+username);
		}
		else {
			userName=customer.get(0).getEmail();
			password=customer.get(0).getPwd();
			authorities=new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
		}
		
		// TODO Auto-generated method stub
		return new User(userName, password, authorities);
	}

}
