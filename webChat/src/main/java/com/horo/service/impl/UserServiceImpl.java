package com.horo.service.impl;

import com.horo.mapper.UserMapper;
import com.horo.pojo.User;
import com.horo.service.UserService;
import com.horo.utils.JwtHelper;
import com.horo.utils.Result;
import com.horo.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Author iove
 * @Date 2025/1/24 下午9:13
 * @Version 1.0
 * @Description TODO
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private UserMapper usermapper;
	@Override
	public Result login(User loginUser) {
		User user=usermapper.getUser(loginUser);
		if(user==null){
			return Result.error("用户未注册");
		}
		log.info("登录用户信息：{}",user);
		String token=jwtHelper.createToken(Long.valueOf(user.getId()));
		Map userInfo=new HashMap();
		userInfo.put("id",user.getId());
		userInfo.put("username",user.getUsername());
		userInfo.put("token",token);
		return Result.ok(userInfo);
	}
}
