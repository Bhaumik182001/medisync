package com.bhaumik18.medisync.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        // We use 127.0.0.1 instead of localhost to bypass IPv6 routing bugs
        // We hardcode the credentials to completely bypass Spring's YAML parser
        ConnectionString connectionString = new ConnectionString(
                "mongodb://rootadmin:rootpassword@127.0.0.1:27017/medisync_identity?authSource=admin"
        );
        
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        
        return MongoClients.create(mongoClientSettings);
    }
}
