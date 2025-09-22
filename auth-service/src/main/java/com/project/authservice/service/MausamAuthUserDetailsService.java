package com.project.authservice.service;

import com.project.authservice.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
