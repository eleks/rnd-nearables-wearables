package com.eleks.controller;

import static com.eleks.utils.Constants.AuthenticationControllerConstants.AUTHENTICATION_URL;
import static com.eleks.utils.Constants.AuthenticationControllerConstants.PASSWORD_REQUEST_PARAM;
import static com.eleks.utils.Constants.AuthenticationControllerConstants.USERNAME_REQUEST_PARAM;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eleks.exception.AuthenticationException;
import com.eleks.model.AuthenticationResponse;
import com.eleks.model.UserCredentials;
import com.eleks.model.db.User;
import com.eleks.service.AuthService;
import com.eleks.service.EmployeesService;
import com.eleks.service.HttpService;
import com.eleks.service.SessionService;
import com.eleks.service.UserService;
import com.eleks.utils.Constants;
import com.eleks.utils.SessionUtils;

@RestController
public class AuthenticationController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private SessionService sessionService;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmployeesService empService;

	@Autowired
	private HttpService httpService;

	@RequestMapping(value = AUTHENTICATION_URL, method = RequestMethod.POST)
	public AuthenticationResponse authenticate(@RequestParam(USERNAME_REQUEST_PARAM) String userName,
			@RequestParam(PASSWORD_REQUEST_PARAM) String password) throws Exception {
		LOG.debug("Processing auth request for user " + userName);

		try {

			final HttpResponse httpResponse = httpService.executeRequest(Constants.TEAMPRO_AUTHENTICATION_URL, new UserCredentials(
					userName, password));
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
				throw new AuthenticationException("Invalid credentials");
			}

			User user = userService.getUserByName(userName);			
			final String uuid = SessionUtils.getSessionIdFromCredentials(userName, password);

			if (user != null) {
				user.setAccessToken(uuid);
			} else {
				user = new User(userName, uuid);
			}
			user.setEmployee(empService.findByUserName(userName));
			userService.saveUser(user);

			LOG.debug("AccessToken generated for user " + userName);

			return new AuthenticationResponse(uuid, userName);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

}
