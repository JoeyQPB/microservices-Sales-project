package com.joey.ProductServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.joey.ProductServer.repository")
public class MongoConfig {

}
