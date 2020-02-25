package com.ProjectService.service;

import java.util.List;

import com.ProjectService.model.Role;
import com.ProjectService.model.Usero;

public interface UserService {
	class IllegalUsernameException extends RuntimeException {
	}

	class IncorrectEmailException extends RuntimeException {
	}

	void addUser(Usero user) throws IllegalUsernameException, IncorrectEmailException;

	void removeUser(Usero user) throws IllegalUsernameException;

	void changeRoleUser(Usero user, Role role);

	Usero verifyUser(String password, String username);
	
	List<Usero> findAll();
	
	Usero findUsero(Usero usero);
	
	void updateUsero(Usero usero);

}