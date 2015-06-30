package com.eleks.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleks.model.teampro.Employee;
import com.eleks.model.teampro.EmployeesRoot;
import com.eleks.repository.EmployeeRepository;
import com.eleks.service.EmployeesService;
import com.eleks.service.HttpService;
import com.eleks.utils.Constants;

@Service
public class EmployeesServiceImpl implements EmployeesService {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmployeesServiceImpl.class);
	
	@Autowired
	private HttpService httpService;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getEmployeesFromTeamProServer() {
		LOG.debug("Started");
		
		try {
			final HttpResponse httpResponse = httpService.executeRequest(Constants.TEAMPRO_EMPLOYEES_URL, Constants.DEFAULT_CREDENTIALS);

    		final JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[] {EmployeesRoot.class}, null);
    		LOG.debug("JAXB: " + jaxbContext.toString());
    		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    		final EmployeesRoot employeesRoot = (EmployeesRoot) unmarshaller.unmarshal(httpResponse.getEntity().getContent());
    		
    		LOG.debug("Finished");
    		
    		return employeesRoot.getEmployees();
    		
		} catch (IOException | JAXBException e) {
			LOG.error(e.getMessage(), e);
			return new ArrayList<Employee>();
		}
	}

	@Override
	public void saveEmployees(List<Employee> employees) {
		employeeRepository.save(employees);
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}

}
