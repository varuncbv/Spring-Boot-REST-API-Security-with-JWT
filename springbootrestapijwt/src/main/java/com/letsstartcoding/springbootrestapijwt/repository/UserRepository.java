package com.letsstartcoding.springbootrestapijwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letsstartcoding.springbootrestapijwt.model.*;

public interface UserRepository extends JpaRepository<User,Long> {
	
	User findOneByUsername(String username);

}
