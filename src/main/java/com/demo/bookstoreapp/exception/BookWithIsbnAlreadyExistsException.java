package com.demo.bookstoreapp.exception;

public class BookWithIsbnAlreadyExistsException extends RuntimeException {

    public BookWithIsbnAlreadyExistsException ( String isbn ) {
    super("Book already exists with ISBN " + isbn);
  }
}
