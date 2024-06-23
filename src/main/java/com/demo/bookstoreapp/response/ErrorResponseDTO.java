package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {

  private String status;
  private int statusCode;
  private String message;

}
