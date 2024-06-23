package com.demo.bookstoreapp.service;

import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.request.BookRequestDTO;
import org.springframework.data.domain.Page;

public interface BookService {

  public Book createBook( BookRequestDTO book);
  public Book updateBook( String id, BookRequestDTO book);
  public void deleteBook( String id);
  public Book getBook( String id);
  public Page<Book> getBooksByTitle ( String title, int pageNo, int pageSize, String sortBy, String order);
  public Page<Book> getBooksByIsbn ( String isbn, int pageNo, int pageSize, String sortBy, String order);
  public Page<Book> getAllBooks( int pageNo, int pageSize, String sortBy, String order);

}
