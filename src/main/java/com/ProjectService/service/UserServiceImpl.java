package com.ProjectService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectService.model.Role;
import com.ProjectService.model.Usero;
import com.ProjectService.repository.UserRepository;
import com.ProjectService.utils.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void addUser(Usero user) throws IllegalUsernameException, IncorrectEmailException {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!user.getEmail().matches(regex))
			throw new IncorrectEmailException();
		if (!user.getUsername().matches("[a-zA-Z]+"))
			throw new IllegalUsernameException();
		user.setPassword(StringUtils.encryptSHA256(user.getPassword()));
		userRepository.save(user);

	}

	@Override
	public void removeUser(Usero user) throws IllegalUsernameException {
		userRepository.delete(user);

	}

	@Override
	public void changeRoleUser(Usero user, Role role) {
		user.setRole(role);
		userRepository.save(user);

	}

	@Override
	public Usero verifyUser(String password, String username) {
		Iterable<Usero> users = userRepository.findAll();
		for (Usero list : users)
			if (list.getPassword().equals(StringUtils.encryptSHA256(password)) && list.getUsername().equals(username)) {
				Usero user = list;
				return user;
			}
		return null;
	}

	@Override
	public List<Usero> findAll() {
		List<Usero> useros = (List<Usero>) userRepository.findAll();
		return useros;
	}

	public Usero findUsero(Usero usero) {
		List<Usero> useros = (List<Usero>) userRepository.findAll();
		for (Usero list : useros) {
			if (list.getUsername().equals(usero.getUsername()))
				return list;
		}
		return null;
	}

	@Override
	public void updateUsero(Usero usero) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (!usero.getEmail().matches(regex))
			throw new IncorrectEmailException();
		if (!usero.getUsername().matches("[a-zA-Z]+"))
			throw new IllegalUsernameException();
		userRepository.save(usero);

	}

}
