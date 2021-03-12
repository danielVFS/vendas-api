package com.daniel.api.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.daniel.api.secutiry.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
