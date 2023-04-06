package com.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capstone.entity.Employee;
import com.capstone.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	//This method maps the root URL of the application to the login page.
	@RequestMapping("/")
	public ModelAndView LoginPage() {
		return new ModelAndView("Login", "command", new Employee());
	}

	//This method logs in the employee if the credentials are valid, or displays an error message if they are not.
	@RequestMapping("/loginUser")
	public ModelAndView checkLogin(@ModelAttribute("command") Employee employee) {
		ModelAndView modelAndView = new ModelAndView();

		if (employee.getUsername() == null || employee.getUsername().isEmpty() || employee.getPassword() == null
				|| employee.getPassword().isEmpty()) {
			String message = "Please enter both username and password";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("Login");
		} else {
			Employee loggedInEmployee = employeeService.login(employee);

			if (loggedInEmployee != null) {
				modelAndView.addObject("employee", loggedInEmployee);
				modelAndView.setViewName("redirect:/home");
			} else {
				String message = "Login failed. Try again";
				modelAndView.addObject("message", message);
				modelAndView.setViewName("Login");
			}
		}
		return modelAndView;
	}

	// This method maps the "/signup" URL to the sign-up page.
	@RequestMapping("/signup")
	public ModelAndView SignUpPage() {
		return new ModelAndView("SignUp", "command", new Employee());
	}

	// This method adds a new employee to the database if the employee does not already exist, or displays an error message if the employee is already registered.
	@RequestMapping("/signedup")
	public ModelAndView signUp(@ModelAttribute("command") Employee employee) {
		ModelAndView modelAndView = new ModelAndView();
		String message = null;

		int signUpEmployee = employeeService.addEmployee(employee);

		if (signUpEmployee > 0) {
			message = "Successfully signed up. Log In";

		} else {
			message = "You are already registered. Login";
		}
		modelAndView.addObject("message", message);
		modelAndView.setViewName("Login");
		return modelAndView;
	}
}
