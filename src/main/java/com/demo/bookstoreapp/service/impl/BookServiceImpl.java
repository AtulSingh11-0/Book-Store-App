package com.demo.bookstoreapp.service.impl;

import com.demo.bookstoreapp.exception.BookWithIdNotFoundException;
import com.demo.bookstoreapp.exception.BookWithIsbnAlreadyExistsException;
import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.repository.BookRepository;
import com.demo.bookstoreapp.request.BookRequestDTO;
import com.demo.bookstoreapp.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository repository;

  @Override
  public Book createBook(BookRequestDTO bookRequest) {
    checkIsbnExists(bookRequest.getIsbn(), false);

    Book newBook = buildBook(bookRequest);
    repository.save(newBook);
    log.info("Book with ISBN {} created successfully", bookRequest.getIsbn());
    return newBook;
  }

  @Override
  public Book updateBook(String id, BookRequestDTO bookRequest) {
    checkBookExistsById(id);
    checkIsbnExists(bookRequest.getIsbn(), true);

    Book updatedBook = buildBook(id, bookRequest);
    repository.save(updatedBook);
    log.info("Book with ID {} updated successfully", id);
    return updatedBook;
  }

  @Override
  public void deleteBook(String id) {
    checkBookExistsById(id);
    repository.deleteById(id);
    log.info("Book with ID {} deleted successfully", id);
  }

  @Override
  public Book getBook(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new BookWithIdNotFoundException(id));
  }

  @Override
  public Page<Book> getBooksByTitle(String title, int pageNo, int pageSize, String sortBy, String order) {
    return getBooksByField("title", title, pageNo, pageSize, sortBy, order);
  }

  @Override
  public Page<Book> getBooksByIsbn(String isbn, int pageNo, int pageSize, String sortBy, String order) {
    return getBooksByField("isbn", isbn, pageNo, pageSize, sortBy, order);
  }

  @Override
  public Page<Book> getAllBooks(int pageNo, int pageSize, String sortBy, String order) {
    Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    return repository.findAll(pageable);
  }

  private Book buildBook(BookRequestDTO bookRequest) {
    return Book.builder()
        .title(bookRequest.getTitle())
        .author(bookRequest.getAuthor())
        .summary(bookRequest.getSummary())
        .publishYear(bookRequest.getPublishYear())
        .isbn(bookRequest.getIsbn())
        .build();
  }

  private Book buildBook(String id, BookRequestDTO bookRequest) {
    return Book.builder()
        .id(id)
        .title(bookRequest.getTitle())
        .author(bookRequest.getAuthor())
        .summary(bookRequest.getSummary())
        .publishYear(bookRequest.getPublishYear())
        .isbn(bookRequest.getIsbn())
        .build();
  }

  private void checkIsbnExists(String isbn, boolean isUpdate) {
    Optional<Book> book = repository.findByIsbn(isbn);
    if (book.isPresent() && !isUpdate) {
      throw new BookWithIsbnAlreadyExistsException(isbn);
    }
  }

  private void checkBookExistsById(String id) {
    if (!repository.existsById(id)) {
      throw new BookWithIdNotFoundException(id);
    }
  }

  private Page<Book> getBooksByField(String fieldName, String value, int pageNo, int pageSize, String sortBy, String order) {
    Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Optional<Page<Book>> books = switch ( fieldName ) {
      case "title" -> repository.findByTitleContainingIgnoreCase(value, pageable);
      case "isbn" -> repository.findByIsbnContainingIgnoreCase(value, pageable);
      default -> Optional.empty();
    };

    log.info("Books with {} {} found", fieldName, value);
    return books.orElse(Page.empty());
  }
}
