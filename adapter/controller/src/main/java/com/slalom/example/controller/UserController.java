package com.slalom.example.controller;

import com.slalom.example.controller.model.UserWeb;
import com.slalom.example.domain.entity.User;
import com.slalom.example.usecase.CreateUser;
import com.slalom.example.usecase.FindUser;
import com.slalom.example.usecase.LoginUser;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserController {

	private final CreateUser createUser;
	private final FindUser findUser;
	private final LoginUser loginUser;

	public UserController(final CreateUser createUser, final FindUser findUser, final LoginUser loginUser) {
		this.createUser = createUser;
		this.findUser = findUser;
		this.loginUser = loginUser;
	}

	public UserWeb createUser(final UserWeb userWeb) {
		var userFromWeb = userWeb.toUser();
		User user = createUser.create(userFromWeb);
		return UserWeb.toUserWeb(user);
	}

	public UserWeb login(final String email, final String password) {
		User user = loginUser.login(email, password);
		return UserWeb.toUserWeb(user);
	}

	public UserWeb getUser(final String userId) {
		Optional<User> user = findUser.findById(userId);
		return UserWeb.toUserWeb(user.orElseThrow(() -> new RuntimeException("user not found")));
	}

	public List<UserWeb> allUsers() {
		return findUser.findAllUsers()
			.stream()
			.map(UserWeb::toUserWeb)
			.collect(Collectors.toList());
	}
}
