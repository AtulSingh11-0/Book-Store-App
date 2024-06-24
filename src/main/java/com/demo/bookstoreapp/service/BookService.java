package com.demo.bookstoreapp.service;

import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.request.BookRequestDTO;
import com.demo.bookstoreapp.response.BookResponseDTO;
import org.springframework.data.domain.Page;

public interface BookService {

  public BookResponseDTO createBook( BookRequestDTO book);
  public BookResponseDTO updateBook( String id, BookRequestDTO book);
  public void deleteBook( String id);
  public BookResponseDTO getBook( String id);
  public Page<Book> getBooksByTitle ( String title, int pageNo, int pageSize, String sortBy, String order);
  public Page<Book> getBooksByIsbn ( String isbn, int pageNo, int pageSize, String sortBy, String order);
  public Page<Book> getAllBooks( int pageNo, int pageSize, String sortBy, String order);

}
