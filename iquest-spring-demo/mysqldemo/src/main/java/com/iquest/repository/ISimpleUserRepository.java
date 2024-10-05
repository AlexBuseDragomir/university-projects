package com.iquest.repository;

import com.iquest.pojo.SimpleUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISimpleUserRepository extends JpaRepository<SimpleUser, Integer> {
	
	// additional method that finds an user by name
	// implemented in the back by using reflection
	Optional<SimpleUser> findByName(String name);
}
