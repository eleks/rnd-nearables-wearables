package com.eleks.controller;

import static com.eleks.utils.Constants.MovementTrackingController.MOVEMENT_SAVE_URL;
import static com.eleks.utils.Constants.MovementTrackingController.MOVEMENT_RECENT_URL;

import java.util.List;

import javax.naming.directory.InvalidAttributeValueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eleks.controller.params.MovementParam;
import com.eleks.exception.UnauthorizedException;
import com.eleks.model.db.Movement;
import com.eleks.model.db.Movement.MovementType;
import com.eleks.model.db.Nearable;
import com.eleks.model.db.User;
import com.eleks.service.MovementTrackingService;
import com.eleks.service.NearableService;
import com.eleks.service.UserService;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

@RestController
public class MovementTrackingController {
	private static final Logger LOG = LoggerFactory.getLogger(MovementTrackingController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private NearableService nearableService;

	@Autowired
	private MovementTrackingService movesService;

	@RequestMapping(value = MOVEMENT_SAVE_URL, method = RequestMethod.POST)
	public Movement saveMovement(@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "user_name") String userName, @RequestBody MovementParam movementParam)
			throws InvalidAttributeValueException {
		User user = userService.getUserByName(userName);
		if (user == null || !accessToken.equals(user.getAccessToken())) {
			throw new UnauthorizedException("Access token is not valid");
		}

		Nearable n = nearableService.getByUid(movementParam.getStikerId());
		if (n == null) {
			n = new Nearable();
			n.setName("UNKNOWN");
			n.setUid(movementParam.getStikerId());
			// throw new InvalidAttributeValueException("Unknown sticker uid");
		}

		Movement movement = new Movement();
		movement.setUser(user);
		movement.setType(MovementType.valueOf(movementParam.getType()));
		movement.setTimestamp(movementParam.getTimestamp());
		movement.setNearable(n);

		System.out.println(movement);

		movesService.saveMovement(movement);

		return movement;
	}

	@RequestMapping(value = MOVEMENT_RECENT_URL, method = RequestMethod.GET)
	public List<Movement> getRecent(@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "user_name") String userName) throws InvalidAttributeValueException {
		User user = userService.getUserByName(userName);
		if (user == null || !accessToken.equals(user.getAccessToken())) {
			throw new UnauthorizedException("Access token is not valid");
		}

		List<Movement> result = movesService.getRecent();
		return result;
	}
}
