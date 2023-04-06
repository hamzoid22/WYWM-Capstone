package com.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.entity.Employee;

/* Interface for the Employee entity. Extends the JpaRepository interface and uses the Employee class as the entity type and the String class as the ID type.*/
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

	public Employee getEmployeeByUsernameAndPassword(String username, String password);

	//Custom query to check if an Employee entity exists with the given username, password, first name, and last name.
	@Query(value = "select count(e) from Employee e where e.username = :username AND e.password = :password AND e.firstName = :firstName AND e.lastName = :lastName")
	public int checkIfUserExists(@Param("username") String username, @Param("password") String password,
			@Param("firstName") String firstName, @Param("lastName") String lastName);
}
