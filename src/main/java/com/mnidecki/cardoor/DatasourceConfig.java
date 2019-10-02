package com.mnidecki.cardoor;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/car_rental?serverTimezone=Europe/Warsaw&allowPublicKeyRetrieval=true&useSSL=False")
                .username("mnidecki")
                .password("mnidecki")
                .build();
    }
}