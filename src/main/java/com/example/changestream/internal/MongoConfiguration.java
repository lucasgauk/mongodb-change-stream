package com.example.changestream.internal;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@Configuration
public class MongoConfiguration {

  @Bean
  public Module objectIdModule() {
    SimpleModule module = new SimpleModule();
    return module.addSerializer(ObjectId.class, new ObjectIdSerializer());
  }
}
