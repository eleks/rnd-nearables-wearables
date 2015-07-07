package com.eleks.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleks.model.db.Movement;
import com.eleks.model.db.User;
import com.eleks.repository.MovementRepository;
import com.eleks.service.MovementTrackingService;

@Service
public class MovementTrackingServiceImpl implements MovementTrackingService {
	private static final Logger LOG = LoggerFactory.getLogger(MovementTrackingServiceImpl.class);

	@Autowired
	private MovementRepository movementRepository;

	public void saveMovement(Movement movement) {
		movementRepository.save(movement);
	}

	public List<Movement> getRecent() {
		return makeCollection(movementRepository.findAll());
	}

	public List<Movement> getHistoryFor(User user) {
		return null;
	}

	public static List<Movement> makeCollection(Iterable<Movement> iter) {
		List<Movement> list = new ArrayList<Movement>();
		for (Movement item : iter) {
			list.add(item);
		}
		return list;
	}
}
