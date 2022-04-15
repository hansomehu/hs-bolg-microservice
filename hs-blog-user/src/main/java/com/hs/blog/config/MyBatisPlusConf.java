package com.hs.blog.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.hs.blog.dao.mapper")
public class MyBatisPlusConf {
}
