package com.eleks.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.eleks.model.UserCredentials;

@Service
public class SessionService {

	private final Map<String, UserCredentials> sessions = new ConcurrentHashMap<String, UserCredentials>();
	
	public UserCredentials getUserCredentials(String sessionId) {
		return sessions.get(sessionId);
	}
	
	public void putUserCredentials(String sessionId, UserCredentials userCredentials) {
		sessions.put(sessionId, userCredentials);
	}
}
