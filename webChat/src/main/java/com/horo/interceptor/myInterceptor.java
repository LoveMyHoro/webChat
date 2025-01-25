package com.horo.interceptor;


import com.horo.utils.JwtHelper;
import com.horo.utils.ResultCodeEnum;
import com.horo.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName myInterceptor
 * @Author iove
 * @Date 2025/1/24 下午8:29
 * @Version 1.0
 * @Description TODO
 **/
@Component
@Slf4j
public class myInterceptor implements HandlerInterceptor {

	@Autowired
	JwtHelper jwtHelper;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("拦截器生效");
		String token=request.getHeader("token");
		if(token==null||jwtHelper.isExpiration(token)){
			response.setStatus(401);
			log.info("无有效token");
			return false;
		}
		ThreadLocalUtil.set(jwtHelper.getUserId(token));
		Long id=ThreadLocalUtil.get();
		log.info("拦截器生效，用户id为：{}",id);
		return true;
	}
}
