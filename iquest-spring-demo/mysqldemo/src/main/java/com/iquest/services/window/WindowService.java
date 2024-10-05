package com.iquest.services.window;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iquest.pojo.Order;
import com.iquest.pojo.Window;
import com.iquest.repository.IWindowRepository;

@Service
public class WindowService implements IWindowService {

	@Inject
	IWindowRepository iWindowRepository;

	@Override
	public ResponseEntity<String> findWindowById(int id) {
		Optional<Window> result = iWindowRepository.findById(id);

		if (!result.isPresent()) {
			return new ResponseEntity<>("Window has not been found", HttpStatus.NOT_FOUND);
		} else {
			Window foundWindow = result.get();

			return new ResponseEntity<>(foundWindow.toString(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<String> addNewWindow(String color, String material, 
			double price, Order order) {

		Window newWindow = new Window();
		newWindow.setColor(color);
		newWindow.setMaterial(material);
		newWindow.setPrice(price);
		newWindow.setOrder(order);

		iWindowRepository.save(newWindow);

		return new ResponseEntity<>("The new window has been saved", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateWindowData(int id, String color, String material, double price) {

		Optional<Window> result = iWindowRepository.findById(id);

		if (!result.isPresent()) {
			return new ResponseEntity<>("Window has not been found", HttpStatus.NOT_FOUND);
		} else {
			Window foundWindow = result.get();
			foundWindow.setColor(color);
			foundWindow.setMaterial(material);
			foundWindow.setPrice(price);

			iWindowRepository.save(foundWindow);
		}
		
		return new ResponseEntity<>("Window data has been updated", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteWindowData(int id) {
		Optional<Window> result = iWindowRepository.findById(id);

		if (!result.isPresent()) {
			return new ResponseEntity<>("Window has not been found", HttpStatus.NOT_FOUND);
		} else {
			Window foundWindow = result.get();

			iWindowRepository.delete(foundWindow);
		}
		return new ResponseEntity<>("Window has been deleted", HttpStatus.OK);
	}
}
