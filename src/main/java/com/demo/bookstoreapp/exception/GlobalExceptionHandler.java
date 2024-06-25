package com.demo.bookstoreapp.exception;

import com.demo.bookstoreapp.response.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      BookWithIdNotFoundException.class,
  })
  public ResponseEntity<ErrorResponseDTO> handleBookNotFoundException ( Exception e ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(buildErrorResponse(HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage())));
  }

  @ExceptionHandler({
      BookWithIsbnAlreadyExistsException.class
  })
  public ResponseEntity<ErrorResponseDTO> handleBookAlreadyExistsException ( Exception e ) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(buildErrorResponse(HttpStatus.CONFLICT, Collections.singletonList(e.getMessage())));
  }

  @ExceptionHandler({
      Exception.class
  })
  public ResponseEntity<ErrorResponseDTO> handleGenericException ( Exception e ) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(e.getMessage())));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid (
      MethodArgumentNotValidException e,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request
  ) {
    List< String > errors = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    return ResponseEntity
        .status(statusCode)
        .body(buildErrorResponse((HttpStatus) statusCode, errors));
  }

  private ErrorResponseDTO buildErrorResponse(HttpStatus status, List<String> message) {
    return ErrorResponseDTO.builder()
        .status("error")
        .statusCode(status.value())
        .message(message)
        .build();
  }
}
