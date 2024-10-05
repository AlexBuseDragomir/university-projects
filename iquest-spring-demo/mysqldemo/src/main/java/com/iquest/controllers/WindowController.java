package com.iquest.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iquest.pojo.Order;
import com.iquest.services.window.IWindowService;

@RestController
@RequestMapping(value = "/window")
public class WindowController {
	
	@Inject
	IWindowService iWindowService;
	
	@GetMapping(value = "/find/{id}")
	public ResponseEntity<String> findWindowById(@PathVariable("id") int id) {
		return iWindowService.findWindowById(id);
	}
	
	/*@Transactional
	@PostMapping(value = "/add")
	public @ResponseBody ResponseEntity<String> addNewWindow(@RequestParam String color, 
			@RequestParam String material, @RequestParam int price, @RequestBody Order order) {
		
		return iWindowService.addNewWindow(color, material, price, order);
	}*/
	
	@Transactional
	@PostMapping(value = "/add")
	public @ResponseBody ResponseEntity<String> addNewWindow(@RequestParam String color, 
			@RequestParam String material, @RequestParam int price, @RequestBody Order order) {
		
		return iWindowService.addNewWindow(color, material, price, order);
	}

	@PutMapping(value = "/update/{id}") 
	public ResponseEntity<String> updateWindowData(@PathVariable("id") int id, @RequestParam String color, 
			@RequestParam String material, @RequestParam int price) { 
		 
		return iWindowService.updateWindowData(id, color, material, price); 
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteWindowData(@PathVariable("id") int id) {
		return iWindowService.deleteWindowData(id);
	}
}
