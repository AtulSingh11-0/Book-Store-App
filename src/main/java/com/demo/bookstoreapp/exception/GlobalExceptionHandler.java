package com.demo.bookstoreapp.exception;

import com.demo.bookstoreapp.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
      BookWithIdNotFoundException.class,
  })
  public ResponseEntity<ErrorResponseDTO> handleBookNotFoundException ( Exception e ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()));
  }

  @ExceptionHandler({
      BookWithIsbnAlreadyExistsException.class
  })
  public ResponseEntity<ErrorResponseDTO> handleBookAlreadyExistsException ( Exception e ) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, e.getMessage()));
  }

  @ExceptionHandler({
      Exception.class
  })
  public ResponseEntity<ErrorResponseDTO> handleGenericException ( Exception e ) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
  }

  private ErrorResponseDTO buildErrorResponse(HttpStatus status, String message) {
    return ErrorResponseDTO.builder()
        .status("error")
        .statusCode(status.value())
        .message(message)
        .build();
  }
}
