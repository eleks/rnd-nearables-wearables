package com.eleks.service;

import java.util.List;

import com.eleks.model.teampro.Employee;

public interface EmployeesService {

	List<Employee> getEmployeesFromTeamProServer();
	
	void saveEmployees(List<Employee> employees);
	
	Employee getEmployeeByEmail(String email);
}
