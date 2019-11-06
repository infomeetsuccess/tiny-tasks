package com.coyoapp.tinytask.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coyoapp.tinytask.domain.Task;
import com.coyoapp.tinytask.domain.User;
import com.coyoapp.tinytask.repository.UserRepository;

@Component
public class LoginServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public boolean login(String userName, String password) {
		Optional<User> userOpt = userRepo.findByNameAndPassword(userName, password);
		if (userOpt.isPresent())
			return true;
		return false;
	}

	@Override
	public List<Task> findByName(String userName) {
		Optional<User> userOpt = userRepo.findByName(userName);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return user.getTask();
		}
		return new ArrayList<Task>();
	}

	@Override
	public User register(String userName, String password) {
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		return userRepo.save(user);
	}
}