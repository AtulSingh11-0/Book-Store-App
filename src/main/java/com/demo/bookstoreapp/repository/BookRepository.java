package com.demo.bookstoreapp.repository;

import com.demo.bookstoreapp.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository< Book, String> {

  Optional<Book> findByIsbn( String isbn );
  Optional< Page<Book> > findByTitleContainingIgnoreCase( String title, Pageable pageable );
  Optional< Page<Book> > findByIsbnContainingIgnoreCase( String isbn, Pageable pageable );

}
