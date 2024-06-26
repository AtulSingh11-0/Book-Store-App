package com.demo.bookstoreapp.service;

import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.request.BookRequestDTO;
import com.demo.bookstoreapp.response.BookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

  BookResponseDTO createBook( BookRequestDTO book, MultipartFile image );
  BookResponseDTO updateBook( String id, BookRequestDTO book, MultipartFile image );
  void deleteBook( String id);
  BookResponseDTO getBook( String id);
  Page<Book> getBooksByTitle ( String title, int pageNo, int pageSize, String sortBy, String order);
  Page<Book> getBooksByIsbn ( String isbn, int pageNo, int pageSize, String sortBy, String order);
  Page<Book> getAllBooks( int pageNo, int pageSize, String sortBy, String order);

}
