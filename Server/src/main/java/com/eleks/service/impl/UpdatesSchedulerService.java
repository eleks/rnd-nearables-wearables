package com.eleks.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.eleks.model.teampro.Employee;
import com.eleks.service.EmployeesService;

@Service
public class UpdatesSchedulerService {
	
	@Autowired
	private EmployeesService employeesService;
	
	@PostConstruct
	public void init() {
		final List<Employee> employees = employeesService.getEmployeesFromTeamProServer();
		employeesService.saveEmployees(employees);
	}
	
	@Scheduled(cron = "0 0 0 * * *") // everyday at midnight
	public void fetchEmployees() {
		final List<Employee> employees = employeesService.getEmployeesFromTeamProServer();
		employeesService.saveEmployees(employees);
	}
	
}
