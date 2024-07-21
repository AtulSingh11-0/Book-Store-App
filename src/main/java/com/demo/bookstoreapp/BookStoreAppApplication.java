package com.demo.bookstoreapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
//@CrossOrigin
@EnableWebMvc
@EnableCaching
@SpringBootApplication
public class BookStoreAppApplication {

  public static void main ( String[] args ) {
    SpringApplication.run(BookStoreAppApplication.class, args);
    log.info("BookStoreAppApplication is running... on http://localhost:6969/api/v1/books/");
  }

}
