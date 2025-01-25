package com.horo;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.horo.mapper")
public class webSocketApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(webSocketApplication.class,args);
    }
}
