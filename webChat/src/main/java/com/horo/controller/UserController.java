package com.horo.controller;

import com.horo.pojo.User;
import com.horo.service.UserService;
import com.horo.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserContoller
 * @Author iove
 * @Date 2025/1/24 下午2:26
 * @Version 1.0
 * @Description TODO
 **/
@RestController
@RequestMapping("/user")
@Tag(name = "文章相关接口")
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping("/login")
	@Operation(tags = "登录接口")
	public Result login(@RequestBody User user){
		Result result=userService.login(user);
		log.info("登录成功");
		return result;
	}
	@GetMapping
	@Operation(tags = "获取用户信息")
	public Result getUserInfo(){
		log.info("获取用户信息");
		return Result.ok(null);
	}
}
