package com.project.apigateway.entity;

import com.project.apigateway.enums.Gender;
import com.project.apigateway.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class User implements UserDetails {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private Gender gender;
	private UserRole role;
	private LocalDateTime createdAt;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
		grantedAuthorities.addAll(role.getUserPermissions().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.name()))
				.collect(Collectors.toSet()));		
		return grantedAuthorities;
	}
}
