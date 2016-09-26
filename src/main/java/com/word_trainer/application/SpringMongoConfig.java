package com.word_trainer.application;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Konfiguracja bazy Mongo.
 */
@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {


    @Override
    protected String getDatabaseName() {
        return "myDBName";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1");
    }
}
