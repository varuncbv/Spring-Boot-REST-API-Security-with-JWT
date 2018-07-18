package com.letsstartcoding.springbootrestapijwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.letsstartcoding.springbootrestapijwt.repository.UserRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findOneByUsername(username);
	}

}
