package com.hs.blog.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {

//    @Bean(name = "auth-client-datasource")
//    @ConfigurationProperties(prefix = "spring.auth-client")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

}
