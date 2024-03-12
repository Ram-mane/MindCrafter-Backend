package com.user.bloggs.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.bloggs.entity.User;
import com.user.bloggs.exceptions.ResourceNotFoundException;
import com.user.bloggs.repository.UserRepo;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CustomUserDetailsServices implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user =this.userRepo.findUserByUsername(username).orElseThrow(()-> new ResourceNotFoundException("user", "username", username));

		return user;
	}

}
