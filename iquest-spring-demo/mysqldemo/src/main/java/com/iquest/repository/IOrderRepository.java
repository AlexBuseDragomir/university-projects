package com.iquest.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.iquest.pojo.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
	// can add custom methods here
}
