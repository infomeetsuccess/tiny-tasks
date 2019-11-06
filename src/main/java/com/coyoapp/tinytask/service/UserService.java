package com.coyoapp.tinytask.service;

import java.util.List;

import com.coyoapp.tinytask.domain.Task;
import com.coyoapp.tinytask.domain.User;

public interface UserService {

	public boolean login(String userName, String password);

	public List<Task> findByName(String userName);

	public User register(String userName, String password);

}
