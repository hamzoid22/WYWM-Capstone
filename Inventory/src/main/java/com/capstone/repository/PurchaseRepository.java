package com.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.entity.Purchase;

/*This is a simple interface for a repository for the Purchase entity. It extends the JpaRepository interface 
 * which provides basic CRUD operations for the Purchase entity. 
 * Since there are no additional custom methods defined in this interface, it inherits all the methods from the JpaRepository interface.*/
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

}
