package com.project.authservice.repository;

import com.project.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long>{
	
	public User findUserByUsername(String username);
	
}
