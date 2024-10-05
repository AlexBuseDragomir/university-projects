package com.iquest.services.user;

import java.util.List; 

import org.springframework.http.ResponseEntity;

import com.iquest.pojo.SimpleUser;

public interface ISimpleUserService {
	
	// get all users
	ResponseEntity<List<SimpleUser>> getAll();
	
	// gets user by id taken from URL
	ResponseEntity<String> getUserByUrlId(int id);
	
	// get a particular user by id
	ResponseEntity<SimpleUser> getUserById(int id);
	
	// get a particular user by name
	ResponseEntity<String> getUserByName(String name);
	
	// add a new user
	ResponseEntity<String> addNewUser (String name, int salary);
	
	// update the name and the salary for a user 
	// with a given id 
	ResponseEntity<String> updateUserData(int id, String name, int salary);

	// delete user data for a record with a specified id
	ResponseEntity<String> deleteUserData(int id);
}
