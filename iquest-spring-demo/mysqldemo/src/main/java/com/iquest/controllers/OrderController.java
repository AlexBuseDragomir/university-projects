package com.iquest.controllers;

import javax.inject.Inject; 

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

import com.iquest.services.order.IOrderService;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
	@Inject
	IOrderService iOrderService;
	
	@GetMapping(value = "/find/{id}")
	public ResponseEntity<String> findWindowById(@PathVariable("id") int id) {
		return iOrderService.findOrderById(id);
	}
	
	@PostMapping(value = "/add")
	public @ResponseBody ResponseEntity<String> addNewUser(@RequestParam String details) {
		
		return iOrderService.addNewOrder(details);
	}

	@PutMapping(value = "/update/{id}") 
	public ResponseEntity<String> updateUserData(@PathVariable("id") int id, 
			@RequestParam String details) { 
		 
		return iOrderService.updateOrderData(id, details); 
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteWindowData(@PathVariable("id") int id) {
		return iOrderService.deleteOrderData(id);
	}
}
