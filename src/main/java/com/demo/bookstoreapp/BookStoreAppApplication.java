package com.demo.bookstoreapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookStoreAppApplication {

  public static void main ( String[] args ) {
    SpringApplication.run(BookStoreAppApplication.class, args);
    System.out.println("BookStoreAppApplication is running... on http://localhost:6969/api/v1/books/");
  }

}
