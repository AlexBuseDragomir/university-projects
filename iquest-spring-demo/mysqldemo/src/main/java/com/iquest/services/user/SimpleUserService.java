package com.iquest.services.user;

import java.util.List;  

import java.util.Optional;
import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iquest.pojo.SimpleUser;
import com.iquest.repository.ISimpleUserRepository;

@Service
public class SimpleUserService implements ISimpleUserService {

	@Inject
    ISimpleUserRepository iSimpleUserRepository;
	
    public ResponseEntity<List<SimpleUser>> getAll() {
    	List<SimpleUser> userList = iSimpleUserRepository.findAll();
    	
    	if (userList.size() > 0) {
    		return new ResponseEntity<>(userList, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    }
	
	@Override
    public ResponseEntity<String> getUserByUrlId(int id) {
    	Optional<SimpleUser> result = iSimpleUserRepository.findById(id);
    	
    	if (! result.isPresent()) {
    		return new ResponseEntity<>("User has not been found", HttpStatus.NOT_FOUND);
    	} else {
    		SimpleUser foundUser = result.get();
    		return new ResponseEntity<>(foundUser.toString(), HttpStatus.OK);
    	}
    }
	
	@Override
    public ResponseEntity<SimpleUser> getUserById(int id) {
    	Optional<SimpleUser> result = iSimpleUserRepository.findById(id);
    	
    	if (! result.isPresent()) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	} else {
    		SimpleUser foundUser = result.get();
    		return new ResponseEntity<>(foundUser, HttpStatus.OK);
    	}
    }
	
	@Override
    public ResponseEntity<String> getUserByName(String name) {
    	Optional<SimpleUser> result = iSimpleUserRepository.findByName(name);
    	
    	if (! result.isPresent()) {
    		return new ResponseEntity<>("User has not been found", HttpStatus.NOT_FOUND);
    	} else {
    		SimpleUser foundUser = result.get();
    		return new ResponseEntity<>(foundUser.toString(), HttpStatus.OK);
    	}
    }

	@Override
	public ResponseEntity<String> addNewUser (String name, int salary) {
	
		SimpleUser simpleUser = new SimpleUser();
		simpleUser.setName(name);
		simpleUser.setSalary(salary);
		
		iSimpleUserRepository.save(simpleUser);
		
		return new ResponseEntity<>("The new user has been saved", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> updateUserData(int id, String name, int salary) {
		Optional<SimpleUser> result = iSimpleUserRepository.findById(id);
    	
    	if (! result.isPresent()) {
    		return new ResponseEntity<>("User has not been found", HttpStatus.NOT_FOUND);
    	} else {
    		SimpleUser foundUser = result.get();
    		foundUser.setName(name);
    		foundUser.setSalary(salary);
    		
    		iSimpleUserRepository.save(foundUser);
    	}
		return new ResponseEntity<>("User data has been updated", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<String> deleteUserData(int id) {
		Optional<SimpleUser> result = iSimpleUserRepository.findById(id);
    	
    	if (! result.isPresent()) {
    		return new ResponseEntity<>("User has not been found", HttpStatus.NOT_FOUND);
    	} else {
    		SimpleUser foundUser = result.get();
    		
    		iSimpleUserRepository.delete(foundUser);
    	}
		return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
	}
}
