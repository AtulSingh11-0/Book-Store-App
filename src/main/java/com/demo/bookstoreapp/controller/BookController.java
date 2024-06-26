package com.demo.bookstoreapp.controller;

import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.model.Image;
import com.demo.bookstoreapp.request.BookRequestDTO;
import com.demo.bookstoreapp.response.ApiResponseDTO;
import com.demo.bookstoreapp.response.BookResponseDTO;
import com.demo.bookstoreapp.response.ImageDTO;
import com.demo.bookstoreapp.response.PaginationResponseDTO;
import com.demo.bookstoreapp.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book Store API", description = "APIs related to Book operations")
public class BookController {

  @Autowired
  private BookService service;

  @PostMapping(
      value = "/",
      consumes = "multipart/form-data"
  )
  @Operation(summary = "Create a book")
  public ResponseEntity<ApiResponseDTO<BookResponseDTO>> handleCreateBook(
      @Valid @ModelAttribute BookRequestDTO bookRequest,
      @RequestParam("image") MultipartFile image) {

    BookResponseDTO book = service.createBook(bookRequest, image);
    log.debug(String.valueOf(book));

    return new ResponseEntity<>(ApiResponseDTO.<BookResponseDTO>builder()
        .status("created")
        .statusCode(HttpStatus.CREATED.value())
        .message("Book created successfully")
        .data(book)
        .build(), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a book")
  public ResponseEntity<ApiResponseDTO<BookResponseDTO>> handleUpdateBook(
      @PathVariable String id,
      @RequestPart(name = "book") @Valid BookRequestDTO bookRequest,
      @RequestPart(name = "image")MultipartFile image ) {

    BookResponseDTO book = service.updateBook(id, bookRequest, image);

    return new ResponseEntity<>(ApiResponseDTO.<BookResponseDTO>builder()
        .status("updated")
        .statusCode(HttpStatus.OK.value())
        .message("Book updated successfully")
        .data(book)
        .build(), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a book")
  public ResponseEntity<ApiResponseDTO<Boolean>> handleDeleteBook(@PathVariable String id) {
    service.deleteBook(id);

    return new ResponseEntity<>(ApiResponseDTO.<Boolean>builder()
        .status("deleted")
        .statusCode(HttpStatus.OK.value())
        .message("Book deleted successfully")
        .data(true)
        .build(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a book by ID")
  public ResponseEntity<ApiResponseDTO<BookResponseDTO>> handleGetBook(@PathVariable String id) {
    BookResponseDTO book = service.getBook(id);
    log.debug(String.valueOf(book));

    return new ResponseEntity<>(ApiResponseDTO.<BookResponseDTO>builder()
        .status("success")
        .statusCode(HttpStatus.OK.value())
        .message("Book retrieved successfully")
        .data(book)
        .build(), HttpStatus.OK);
  }

  @GetMapping("/search-by-title")
  @Operation(summary = "Get books by title")
  public ResponseEntity<ApiResponseDTO<List<BookResponseDTO>>> handleGetBooksByTitle(
      @RequestParam String title,
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {

    Page<Book> pagination = service.getBooksByTitle(title, pageNo, pageSize, sortBy, order);
    List<BookResponseDTO> books = pagination.getContent().stream()
        .map(this::convertToBookResponseDTO)
        .collect(Collectors.toList());
    PaginationResponseDTO paginationData = buildPaginationResponse(pagination, order, sortBy);

    return new ResponseEntity<>(ApiResponseDTO.<List<BookResponseDTO>>builder()
        .status("success")
        .statusCode(HttpStatus.OK.value())
        .message(!books.isEmpty() ? "Books retrieved successfully" : "No books found matching the search criteria")
        .data(books)
        .pagination(paginationData)
        .build(), HttpStatus.OK);
  }


  @GetMapping("/search-by-isbn")
  @Operation(summary = "Get books by ISBN")
  public ResponseEntity<ApiResponseDTO<List<BookResponseDTO>>> handleGetBooksByIsbn(
      @RequestParam String isbn,
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {

    Page<Book> pagination = service.getBooksByIsbn(isbn, pageNo, pageSize, sortBy, order);
    List<BookResponseDTO> books = pagination.getContent().stream()
        .map(this::convertToBookResponseDTO)
        .collect(Collectors.toList());
    PaginationResponseDTO paginationData = buildPaginationResponse(pagination, order, sortBy);

    return new ResponseEntity<>(ApiResponseDTO.<List<BookResponseDTO>>builder()
        .status("success")
        .statusCode(HttpStatus.OK.value())
        .message(!books.isEmpty() ? "Books retrieved successfully" : "No books found matching the search criteria")
        .data(books)
        .pagination(paginationData)
        .build(), HttpStatus.OK);
  }

  @GetMapping("")
  @Operation(summary = "Get all books")
  public ResponseEntity<ApiResponseDTO<List<BookResponseDTO>>> handleGetAllBooks(
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {

    Page<Book> pagination = service.getAllBooks(pageNo, pageSize, sortBy, order);
    List<BookResponseDTO> books = pagination.getContent().stream()
        .map(this::convertToBookResponseDTO)
        .collect(Collectors.toList());
    PaginationResponseDTO paginationData = buildPaginationResponse(pagination, order, sortBy);

    return new ResponseEntity<>(ApiResponseDTO.<List<BookResponseDTO>>builder()
        .status("success")
        .statusCode(HttpStatus.OK.value())
        .message(!books.isEmpty() ? "Books retrieved successfully" : "No books found")
        .data(books)
        .pagination(paginationData)
        .build(), HttpStatus.OK);
  }

  private BookResponseDTO convertToBookResponseDTO( Book book) {
    return BookResponseDTO.builder()
        .id(book.getId())
        .title(book.getTitle())
        .author(book.getAuthor())
        .summary(book.getSummary())
        .publishYear(book.getPublishYear())
        .isbn(book.getIsbn())
        .image(buildImageDTO(book.getImage()))
        .createdDate(book.getCreatedDate())
        .lastModifiedDate(book.getLastModifiedDate())
        .build();
  }

  private PaginationResponseDTO buildPaginationResponse(Page<Book> pagination, String order, String sortBy) {
    return PaginationResponseDTO.builder()
        .currentPage(pagination.getNumber())
        .currentItem(pagination.getNumberOfElements())
        .totalPages(pagination.getTotalPages())
        .totalItems(pagination.getTotalElements())
        .hasNext(pagination.hasNext())
        .hasPrevious(pagination.hasPrevious())
        .sort(pagination.getSort().toString())
        .sortBy(sortBy)
        .order(order)
        .build();
  }

  private ImageDTO buildImageDTO (Image image) {
    ImageDTO imageDTO = ImageDTO.builder()
        .id(image.getId())
        .publicId(image.getPublicId())
        .url(image.getUrl())
        .createdDate(image.getCreatedDate())
        .lastModifiedDate(image.getLastModifiedDate())
        .build();
    log.debug(String.valueOf(imageDTO));
    return imageDTO;
  }
}
