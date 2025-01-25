package com.horo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebConfig
 * @Author iove
 * @Date 2025/1/24 下午7:22
 * @Version 1.0
 * @Description TODO
 **/
@Configuration
@EnableCaching
@Slf4j
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	HandlerInterceptor handlerInterceptor;

	/**
	 * 1.添加拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerInterceptor).excludePathPatterns("/user/login");
	}

	/**
	 * 2.1.配置doc.html在线接口文档
	 * @return
	 */
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

	/**
	 * 2.2.设置静态资源映射路径
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("开启静态资源映射");
		registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}

	/**
	 * 3.扫描带有 @ServerEndpoint 注解的 WebSocket 端点，并将它们注册到服务器容器中，
	 * 使得它们可以处理 WebSocket 连接请求
	 * @return
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter();
	}


}
