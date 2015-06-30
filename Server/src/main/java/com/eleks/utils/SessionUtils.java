package com.eleks.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SessionUtils {

	public static String getSessionIdFromCredentials(String userName, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		final MessageDigest digest = MessageDigest.getInstance("MD5");
        final byte[] hashedBytes = digest.digest((userName + password).getBytes("UTF-8"));
        
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hashedBytes.length; i++) {
        	stringBuilder.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
		
        return stringBuilder.toString();
	}
	
}
