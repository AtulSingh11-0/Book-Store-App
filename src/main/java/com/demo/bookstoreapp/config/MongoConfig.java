package com.demo.bookstoreapp.config;

import com.demo.bookstoreapp.model.Book;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {

  @Value ("${spring.data.mongodb.uri}")
  private String uri;

  @Value ("${spring.data.mongodb.database}")
  private String database;

  @Bean
  public AuditorAware<String> auditorAware () {
    return new AuditWareConfig();
  }

  @NotNull
  @Override
  protected String getDatabaseName () {
    return database;
  }

  @Override
  protected void configureClientSettings ( @NotNull MongoClientSettings.Builder builder ) {
    builder.applyConnectionString(new ConnectionString(uri));

    builder.applyToConnectionPoolSettings(settings -> settings.maxConnectionLifeTime(1000, TimeUnit.MILLISECONDS)
        .minSize(5)
        .maxSize(10)
        .maintenanceFrequency(10, TimeUnit.MILLISECONDS)
        .maintenanceInitialDelay(11, TimeUnit.MILLISECONDS)
        .maxConnectionIdleTime(30, TimeUnit.SECONDS)
        .maxWaitTime(15, TimeUnit.MILLISECONDS));
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoDbFactory());
  }

  @EventListener ( ContextRefreshedEvent.class)
  public void initIndexes() {
    MongoTemplate mongoTemplate = null;
    try {
      mongoTemplate = mongoTemplate();
    } catch (Exception e) {
      log.error("Error creating MongoTemplate", e);
    }

    if (mongoTemplate != null) {
      IndexOperations indexOps = mongoTemplate.indexOps(Book.class);
      indexOps.ensureIndex(new Index().on("title", Sort.Direction.ASC));
    }
  }
}
