package com.coyoapp.tinytask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coyoapp.tinytask.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByNameAndPassword(String name, String password);

	public Optional<User> findByName(String name);

}
