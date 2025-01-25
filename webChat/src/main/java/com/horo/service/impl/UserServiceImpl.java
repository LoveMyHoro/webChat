package com.horo.service.impl;

import com.horo.pojo.User;
import com.horo.service.UserService;
import com.horo.utils.JwtHelper;
import com.horo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Author iove
 * @Date 2025/1/24 下午9:13
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private JwtHelper jwtHelper;
	@Override
	public Result login(User user) {
		String token= jwtHelper.createToken(1L);
		return Result.ok(token);
	}
}
