package com.horo.service;

import com.horo.pojo.User;
import com.horo.utils.Result;

import java.util.ResourceBundle;

public interface UserService {
	Result login(User user);
}
