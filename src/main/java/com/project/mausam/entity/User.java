package com.project.mausam.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.mausam.enums.Gender;
import com.project.mausam.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "m_users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstName;
	private String lastName;
	@Column(nullable = false, unique = true)
	private String username;
	private String password;
	@Column(nullable = true, unique = true)
	private String email;
	@Enumerated(EnumType.STRING)
	private Gender gender; 
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private LocalDateTime createdAt;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.getRoleName()));
	}
}
