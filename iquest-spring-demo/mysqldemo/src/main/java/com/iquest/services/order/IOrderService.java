package com.iquest.services.order;

import org.springframework.http.ResponseEntity;

public interface IOrderService {

	// get an order by id
	ResponseEntity<String> findOrderById(int id);

	// add a new order
	ResponseEntity<String> addNewOrder(String details);

	// update the details for an order given by its id
	ResponseEntity<String> updateOrderData(int id, String details);

	// delete order data for a record with a specified id
	ResponseEntity<String> deleteOrderData(int id);
}
