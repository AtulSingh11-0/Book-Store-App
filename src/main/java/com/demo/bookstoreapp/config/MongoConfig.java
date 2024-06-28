package com.demo.bookstoreapp.config;

import com.demo.bookstoreapp.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Bean
  public AuditorAware<String> auditorAware () {
    return new AuditWareConfig();
  }

  public MongoConfig( MongoTemplate mongoTemplate ) {
    this.mongoTemplate = mongoTemplate;
    IndexOperations indexOps = mongoTemplate.indexOps(Book.class);
    indexOps.ensureIndex(new Index().on("title", Sort.Direction.ASC));
  }
}
