package com.project.mausam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mausam.entity.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long>{

}
