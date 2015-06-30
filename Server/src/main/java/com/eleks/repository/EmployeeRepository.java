package com.eleks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eleks.model.teampro.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	Employee findByEmail(String email);
}
