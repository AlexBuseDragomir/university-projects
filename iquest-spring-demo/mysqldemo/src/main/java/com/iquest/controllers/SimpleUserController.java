package com.iquest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iquest.pojo.SimpleUser;
import com.iquest.pojo.SimpleUserList;
import com.iquest.services.user.ISimpleUserService;
import com.iquest.utility.FileUtility;

@RestController
@RequestMapping(value = "/user")
public class SimpleUserController {

	@Inject
	ISimpleUserService iSimpleUserService;
	
	@Inject 
	FileUtility fileUtility;
	
	@Inject
	SimpleUserList simpleUserList;

	@GetMapping(value = "/find/name")
	public ResponseEntity<String> getUserByName(@RequestParam String name) {

		return iSimpleUserService.getUserByName(name);
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<List<SimpleUser>> getAll() {
		List<SimpleUser> userList = iSimpleUserService.getAll().getBody();
		
		simpleUserList.setUserList(userList); 
		fileUtility.saveUserListToFileOnDesktop("file_list.xml", simpleUserList);
		
		return iSimpleUserService.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public ResponseEntity<String> getUserByUrlId(@PathVariable("id") int id) {
		return iSimpleUserService.getUserByUrlId(id);
	}

	@GetMapping(value = "/find/id", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<SimpleUser> getUserById(@RequestParam int id) {
		SimpleUser foundUser = iSimpleUserService.getUserById(id).getBody(); 
		
		fileUtility.saveUserToFileOnDesktop("file_user.xml", foundUser);

		return iSimpleUserService.getUserById(id);

	}

	@PostMapping(value = "/add")
	public @ResponseBody ResponseEntity<String> addNewUser(@RequestParam String name, 
			@RequestParam int salary) {
		
		return iSimpleUserService.addNewUser(name, salary);
	}

	@PutMapping(value = "/update/{id}") 
	public ResponseEntity<String> updateUserData(@PathVariable("id") int id, 
			@RequestParam String name, @RequestParam int salary) { 
		 
		return iSimpleUserService.updateUserData(id, name, salary); 
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteUserData(@PathVariable("id") int id) {
		return iSimpleUserService.deleteUserData(id);
	}
}
