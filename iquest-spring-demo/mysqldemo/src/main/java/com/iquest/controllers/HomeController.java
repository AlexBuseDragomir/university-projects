package com.iquest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<String> printHelloMessage() {
		return new ResponseEntity<>("Hello world, iQuest", HttpStatus.OK);
	}
}
