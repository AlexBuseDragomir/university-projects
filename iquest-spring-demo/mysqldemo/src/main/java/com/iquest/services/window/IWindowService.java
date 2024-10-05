package com.iquest.services.window;

import org.springframework.http.ResponseEntity;

import com.iquest.pojo.Order;

public interface IWindowService {

	// get a window by id
	ResponseEntity<String> findWindowById(int id);

	// add a new window
	ResponseEntity<String> addNewWindow(String color, String material, 
			double price, Order order);

	// update the details for a window given by its id
	ResponseEntity<String> updateWindowData(int id, String color,
			String material, double price);

	// delete window data for a record with a specified id
	ResponseEntity<String> deleteWindowData(int id);
}