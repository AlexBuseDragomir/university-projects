package com.iquest.services.order;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iquest.pojo.Order;
import com.iquest.repository.IOrderRepository;

@Service
public class OrderService implements IOrderService {

	@Inject
	IOrderRepository iOrderRepository;
	
	@Override
	public ResponseEntity<String> findOrderById(int id) {
		Optional<Order> result = iOrderRepository.findById(id);

		if (!result.isPresent()) {
			return new ResponseEntity<>("Order has not been found", HttpStatus.NOT_FOUND);
		} else {
			Order foundOrder = result.get();

			return new ResponseEntity<>(foundOrder.toString(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<String> addNewOrder(String details) {
		
		Order newOrder = new Order();
		newOrder.setDetails(details);
		
		iOrderRepository.save(newOrder);
		
		return new ResponseEntity<>("The new order has been saved", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateOrderData(int id, String details) {
		Optional<Order> result = iOrderRepository.findById(id);

		if (!result.isPresent()) {
			return new ResponseEntity<>("Order has not been found", HttpStatus.NOT_FOUND);
		} else {
			Order foundOrder = result.get();
			foundOrder.setDetails(details);

			iOrderRepository.save(foundOrder);
		}
		
		return new ResponseEntity<>("Window data has been updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteOrderData(int id) {
		Optional<Order> result = iOrderRepository.findById(id);

		if (!result.isPresent()) {
			return new ResponseEntity<>("Order has not been found", HttpStatus.NOT_FOUND);
		} else {
			Order foundOrder = result.get();

			iOrderRepository.delete(foundOrder);
		}
		return new ResponseEntity<>("Order has been deleted", HttpStatus.OK);
	}
}
