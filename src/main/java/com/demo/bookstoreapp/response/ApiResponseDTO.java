package com.demo.bookstoreapp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude( JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {

  private String status;
  private int statusCode;
  private String message;
  private T data;
  private PaginationResponseDTO pagination;

}
