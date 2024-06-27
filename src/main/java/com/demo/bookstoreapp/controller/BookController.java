package com.demo.bookstoreapp.controller;

import com.demo.bookstoreapp.model.Book;
import com.demo.bookstoreapp.model.Image;
import com.demo.bookstoreapp.request.BookRequestDTO;
import com.demo.bookstoreapp.response.*;
import com.demo.bookstoreapp.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @ApiResponses( value = {
      @ApiResponse(responseCode = "201", description = "Book created successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = "application" +
          "/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<BookResponseDTO>> handleCreateBook(
      @Parameter (description = "Book details", required = true)
      @Valid @ModelAttribute BookRequestDTO bookRequest,
      @Parameter(description = "Book cover image", required = true)
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

  @PutMapping(
      value = "/{id}",
      consumes = "multipart/form-data"
  )
  @Operation(summary = "Update a book")
  @ApiResponses( value = {
      @ApiResponse(responseCode = "200", description = "Book updated successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(mediaType = "application" +
          "/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
      @ApiResponse(responseCode = "404", description = "Book with Id Not Found", content = { @Content(mediaType =
          "application" +
          "/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<BookResponseDTO>> handleUpdateBook(
      @Parameter(description = "Book ID to update", required = true)
      @PathVariable String id,
      @Parameter(description = "Updated book details", required = true)
      @Valid @ModelAttribute BookRequestDTO bookRequest,
      @Parameter(description = "Updated book cover image", required = true)
      @RequestParam("image") MultipartFile image) {

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
  @ApiResponses( value = {
      @ApiResponse(responseCode = "200", description = "Book deleted successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "404", description = "Book with Id Not Found", content = { @Content(mediaType =
          "application" +
          "/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<Boolean>> handleDeleteBook(
      @Parameter(description = "Book ID to delete", required = true)
      @PathVariable String id ) {
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
  @ApiResponses( value = {
      @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "404", description = "Book with Id Not Found", content = { @Content(mediaType =
          "application" +
          "/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<BookResponseDTO>> handleGetBook(
      @Parameter(description = "Book ID to retrieve", required = true)
      @PathVariable String id ) {
    BookResponseDTO book = service.getBook(id);

    return new ResponseEntity<>(ApiResponseDTO.<BookResponseDTO>builder()
        .status("success")
        .statusCode(HttpStatus.OK.value())
        .message("Book retrieved successfully")
        .data(book)
        .build(), HttpStatus.OK);
  }

  @GetMapping("/search-by-title")
  @Operation(summary = "Get books by title")
  @ApiResponses( value = {
      @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "200", description = "No books found matching the search criteria", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<List<BookResponseDTO>>> handleGetBooksByTitle(
      @Parameter(description = "Title to search for", required = true)
      @RequestParam String title,
      @Parameter(description = "Page number (default: 0)")
      @RequestParam(defaultValue = "0") int pageNo,
      @Parameter(description = "Number of items per page (default: 10)")
      @RequestParam(defaultValue = "10") int pageSize,
      @Parameter(description = "Sort by field (default: id)")
      @RequestParam(defaultValue = "id") String sortBy,
      @Parameter(description = "Sort order (default: asc)")
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
  @ApiResponses( value = {
      @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "200", description = "No books found matching the search criteria", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<List<BookResponseDTO>>> handleGetBooksByIsbn(
      @Parameter(description = "ISBN to search for", required = true)
      @RequestParam String isbn,
      @Parameter(description = "Page number (default: 0)")
      @RequestParam(defaultValue = "0") int pageNo,
      @Parameter(description = "Number of items per page (default: 10)")
      @RequestParam(defaultValue = "10") int pageSize,
      @Parameter(description = "Sort by field (default: id)")
      @RequestParam(defaultValue = "id") String sortBy,
      @Parameter(description = "Sort order (default: asc)")
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
  @ApiResponses( value = {
      @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "200", description = "No books found", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiResponseDTO.class)) }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType =
          "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)) }),
  })
  public ResponseEntity<ApiResponseDTO<List<BookResponseDTO>>> handleGetAllBooks(
      @Parameter(description = "Page number (default: 0)")
      @RequestParam(defaultValue = "0") int pageNo,
      @Parameter(description = "Number of items per page (default: 10)")
      @RequestParam(defaultValue = "10") int pageSize,
      @Parameter(description = "Sort by field (default: id)")
      @RequestParam(defaultValue = "id") String sortBy,
      @Parameter(description = "Sort order (default: asc)")
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
