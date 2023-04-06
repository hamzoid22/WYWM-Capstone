package com.capstone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.entity.Item;

import jakarta.transaction.Transactional;

/*This interface defines a repository for the Item entity. It extends the JpaRepository interface, which provides basic CRUD operations for the entity. 
 * The interface contains several method declarations annotated with @Query annotations, which define custom queries to be executed against the database.*/
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

//	@Query("SELECT i from Item i where i.category = :category")
	List<Item> findByCategory(@Param("category") String category);

	@Query(value = "select count(i) from Item i where i.name = :name AND i.price = :price AND i.category = :category AND i.costPrice = :costPrice")
	public int checkIfItemExists(@Param("name") String name, @Param("price") double price,
			@Param("category") String category, @Param("costPrice") double costPrice);

	@Transactional
	@Modifying
	@Query("Update Item i SET i.quantity = i.quantity + :incrementQuantity WHERE i.id = :id")
	public int updateQuantity(@Param("id") int id, @Param("incrementQuantity") int quantity);

	@Transactional
	@Modifying
	@Query("Update Item i SET i.quantitySold = i.quantitySold + :amountSold WHERE i.id = :id")
	public int updateQuantitySold(@Param("id") int id, @Param("amountSold") int quantitySold);

	@Query("select i.id FROM Item i WHERE i.name = :name AND i.price = :price AND i.category = :category AND i.costPrice = :costPrice")
	public int findId(@Param("name") String name, @Param("price") double price, @Param("category") String category,
			@Param("costPrice") double costPrice);

	Optional<Item> findById(int id);

	Optional<Item> findByName(String name);

}
