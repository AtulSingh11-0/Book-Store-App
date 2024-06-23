package com.demo.bookstoreapp.exception;

public class BookWithIdNotFoundException extends RuntimeException {

  public BookWithIdNotFoundException ( String id ) {
    super("Book not found with id " + id);
  }

}
