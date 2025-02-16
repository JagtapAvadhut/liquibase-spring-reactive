//package com.reactive.liquibase.config;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import liquibase.integration.spring.SpringLiquibase;
//
//@Configuration
//public class LiquibaseConfig {
//
//    @Bean
//    public CommandLineRunner runLiquibase(SpringLiquibase liquibase) {
//        return args -> {
//            // This will ensure Liquibase runs on startup
//            liquibase.afterPropertiesSet();
//        };
//    }
//}