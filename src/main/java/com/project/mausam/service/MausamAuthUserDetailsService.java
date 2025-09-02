package com.project.mausam.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.mausam.repository.AuthRepository;

@Service
public class MausamAuthUserDetailsService implements UserDetailsService{
	
	private final AuthRepository authRepository;
	
	public MausamAuthUserDetailsService(AuthRepository authRepository) {
		this.authRepository = authRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return authRepository.findUserByUsername(username);
	}

}
