package com.project.mausam.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.mausam.repository.AuthRepository;
import com.project.mausam.service.MausamAuthUserDetailsService;;

@Configuration
@EnableWebSecurity 
public class AuthConfig {
	
	private final AuthRepository authRepository;
	
	public AuthConfig(AuthRepository authRepository) {
		this.authRepository = authRepository;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/mausam/user/signup").permitAll()
						.anyRequest().authenticated())
				.httpBasic(withDefaults()).build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2B, 10);
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new MausamAuthUserDetailsService(authRepository);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(daoAuthenticationProvider);
	}
	
}
