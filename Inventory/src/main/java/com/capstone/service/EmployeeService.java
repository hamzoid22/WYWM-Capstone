package com.capstone.service;

import com.capstone.entity.Employee;

//This interface for manages employees with two methods: login and addEmployee.
public interface EmployeeService {

	public Employee login(Employee employee);
	public int addEmployee(Employee employee);
}
