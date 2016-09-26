package com.word_trainer.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Konfiguracja springa dla aplikacji.
 */
@Configuration
@ComponentScan(basePackages = "com.word_trainer")
@EnableMongoRepositories(basePackages = "com.word_trainer")
class SpringApplicationConfig {

}
