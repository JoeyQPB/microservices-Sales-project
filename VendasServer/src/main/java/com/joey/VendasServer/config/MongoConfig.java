package com.joey.VendasServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.joey.VendasServer.repository")
public class MongoConfig {

}
