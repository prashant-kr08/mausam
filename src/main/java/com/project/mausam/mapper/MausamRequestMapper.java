package com.project.mausam.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.mausam.api.dto.auth.SignUpRequest;
import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.User;
import com.project.mausam.enums.Gender;

@Component
public class MausamRequestMapper {
	
	private final PasswordEncoder passwordEncoder;
	
	public MausamRequestMapper(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public CityMausamRequest getCityMausamRequestByMausam(final Mausam savedMausam) {
		final CityMausamRequest cityMausamRequest = new CityMausamRequest();
		cityMausamRequest.setCityName(savedMausam.getLocation().getCityName());
		cityMausamRequest.setUnits(savedMausam.getUnitsCode());
		return cityMausamRequest;
	}
	
	public User getUserBySignUpRequest(final SignUpRequest signUpRequest) {
		final User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setEmail(signUpRequest.getEmail());
		user.setGender(Gender.getGenderByCode(signUpRequest.getGenderCode()));
		return user;
	}

}
