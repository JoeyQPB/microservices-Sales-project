package com.joey.ClientServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.joey.ClientServer.repository")
public class MongoConfig {

}
