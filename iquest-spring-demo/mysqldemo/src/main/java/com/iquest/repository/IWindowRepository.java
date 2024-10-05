package com.iquest.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iquest.pojo.Window;

@Repository
public interface IWindowRepository extends JpaRepository<Window, Integer> {
	// to add methods here
}
