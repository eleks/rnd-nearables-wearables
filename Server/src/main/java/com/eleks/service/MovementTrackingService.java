package com.eleks.service;

import java.util.List;

import com.eleks.model.db.Movement;
import com.eleks.model.db.User;

public interface MovementTrackingService {
	void saveMovement(Movement movement);
	List<Movement> getRecent();
	List<Movement> getHistoryFor(User user);
}
