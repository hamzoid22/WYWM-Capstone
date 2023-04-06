package com.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.entity.Employee;
import com.capstone.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee login(Employee employee) {
		return employeeRepository.getEmployeeByUsernameAndPassword(employee.getUsername(), employee.getPassword());
	}

	@Override
	public int addEmployee(Employee employee) {
		int exists = employeeRepository.checkIfUserExists(employee.getUsername(), employee.getPassword(), employee.getFirstName(), employee.getLastName());
		if (exists < 1) {
			employeeRepository.save(employee);
			return 1;
		} else {
			return 0;
		}
	}

}
