package com.horo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Author iove
 * @Date 2025/1/24 下午7:22
 * @Version 1.0
 * @Description TODO
 **/
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	HandlerInterceptor handlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerInterceptor).excludePathPatterns("/user/login");
	}

	@Bean
	public OpenAPI openAPI(){
		Info info = new Info()
				.title("网络聊天室")
				.version("1.0")
				.contact(new Contact().name("Horo").url("http://www.horoweb.cn").email("859321454@qq.com"))
				.license(new License().name("Apache 2.0"))
				.termsOfService("http://www.horoweb.cn")
				.description("赫萝的个人聊天室");

		return new OpenAPI().info(info);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("开启静态资源映射");
		registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}
}
