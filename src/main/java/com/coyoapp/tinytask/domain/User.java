package com.coyoapp.tinytask.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Table(name = "tiny_user")
@Entity
@Setter
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false, updatable = false)
	private long userID;

	@Column(name = "user_name", unique = true)
	private String name;

	@CreatedDate
	@Column(name = "created")
	private Instant created;

	@Column(name = "password")
	private String password;

	@OneToMany
	private List<Task> task = new ArrayList<Task>();
}
