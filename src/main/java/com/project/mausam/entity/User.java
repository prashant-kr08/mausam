package com.project.mausam.entity;

import java.time.LocalDateTime;

import com.project.mausam.enums.Gender;
import com.project.mausam.enums.UserRole;

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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	@Enumerated(EnumType.STRING)
	private Gender gender; 
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private LocalDateTime createdAt;
}
