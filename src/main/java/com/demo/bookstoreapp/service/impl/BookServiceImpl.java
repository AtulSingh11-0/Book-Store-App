package com.demo.bookstoreapp.service.impl;

import com.demo.bookstoreapp.exception.BookWithIdNotFoundException;
import com.demo.bookstoreapp.exception.BookWithIsbnAlreadyExistsException;
import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.model.Image;
import com.demo.bookstoreapp.repository.BookRepository;
import com.demo.bookstoreapp.repository.ImageRepository;
import com.demo.bookstoreapp.request.BookRequestDTO;
import com.demo.bookstoreapp.response.BookResponseDTO;
import com.demo.bookstoreapp.response.ImageDTO;
import com.demo.bookstoreapp.service.BookService;
import com.demo.bookstoreapp.service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "books")
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository repository;
  @Autowired
  private ImageRepository imageRepository;
  @Autowired
  private CloudinaryService cloudinaryService;

  @Override
  @CachePut(value = "book", key = "#bookRequest.isbn")
  public BookResponseDTO createBook(
      BookRequestDTO bookRequest,
      MultipartFile image
  ) {
    checkIsbnExists(bookRequest.getIsbn(), false);
    Image savedImage = saveImage(image);

    Book newBook = buildBook(bookRequest);
    newBook.setImage(savedImage);
    repository.save(newBook);

    log.info("Book with ISBN {} created successfully", bookRequest.getIsbn());
    return mapToDto(newBook);
  }

  @Override
  @CachePut(value = "book", key = "#id")
  public BookResponseDTO updateBook(
      String id,
      BookRequestDTO bookRequest,
      MultipartFile image
  ) {
    checkBookExistsById(id);
    checkIsbnExists(bookRequest.getIsbn(), true);

    Optional< Book > book = repository.findById(id);
    if (book.isPresent()) {
      Book existingBook = book.get();
      Book updatedBook = buildBook(id, bookRequest);
      if ( image != null ) {
        Image savedImage = saveImage(image);
        updatedBook.setImage(savedImage);
      }
      updatedBook.setImage(existingBook.getImage());
      updatedBook.setCreatedDate(existingBook.getCreatedDate());

      repository.save(updatedBook);
      log.info("Book with ID {} updated successfully", id);
      return mapToDto(updatedBook);
    } else {
      throw new BookWithIdNotFoundException(id);
    }
  }

  @Override
  @CacheEvict(value = "book", key = "#id")
  public void deleteBook(String id) {
    checkBookExistsById(id);
    repository.findById(id).ifPresent(book -> {
      cloudinaryService.deleteImage(book.getImage().getPublicId());
      imageRepository.deleteById(book.getImage().getId());
    });
    repository.deleteById(id);
    log.info("Book with ID {} deleted successfully", id);
  }

  @Override
  @Cacheable(value = "book", key = "#id")
  public BookResponseDTO getBook(String id) {
    Book book = repository.findById(id)
        .orElseThrow(() -> new BookWithIdNotFoundException(id));
    return mapToDto(book);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "books", key = "#title")
  public Page<Book> getBooksByTitle(
      String title,
      int pageNo,
      int pageSize,
      String sortBy,
      String order
  ) {
    return getBooksByField("title", title, pageNo, pageSize, sortBy, order);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "books", key = "#isbn")
  public Page<Book> getBooksByIsbn(
      String isbn,
      int pageNo,
      int pageSize,
      String sortBy,
      String order
  ) {
    return getBooksByField("isbn", isbn, pageNo, pageSize, sortBy, order);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "books", key = "#pageNo + #pageSize + #sortBy + #order")
  public Page<Book> getAllBooks(
      int pageNo,
      int pageSize,
      String sortBy,
      String order
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    return repository.findAll(pageable);
  }

  private Book buildBook(BookRequestDTO bookRequest) {
    Book book = new Book();
    BeanUtils.copyProperties(bookRequest, book);
    return book;
  }

  private Book buildBook(
      String id,
      BookRequestDTO bookRequest
  ) {
    Book book = buildBook(bookRequest);
    book.setId(id);
    return book;
  }

  private void checkIsbnExists(
      String isbn,
      boolean isUpdate
  ) {
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

  private Page<Book> getBooksByField(
      String fieldName,
      String value,
      int pageNo,
      int pageSize,
      String sortBy,
      String order
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    Optional<Page<Book>> books = switch (fieldName) {
      case "title" -> repository.findByTitleContainingIgnoreCase(value, pageable);
      case "isbn" -> repository.findByIsbnContainingIgnoreCase(value, pageable);
      default -> Optional.empty();
    };

    log.info("Books with {} {} found", fieldName, value);
    return books.orElse(Page.empty());
  }

  private BookResponseDTO mapToDto( Book book) {
    BookResponseDTO dto = BookResponseDTO.builder()
        .image(convertToImageDTO(book.getImage()))
        .build();
    BeanUtils.copyProperties(book, dto);
    return dto;
  }

  private ImageDTO convertToImageDTO (Image image) {
    return ImageDTO.builder()
        .id(image.getId())
        .publicId(image.getPublicId())
        .url(image.getUrl())
        .createdDate(image.getCreatedDate())
        .lastModifiedDate(image.getLastModifiedDate())
        .build();
  }

  private Image saveImage ( MultipartFile image ) {
    Image uploadedImage = cloudinaryService.uploadImage(image);
    return imageRepository.save(uploadedImage);
  }

}
