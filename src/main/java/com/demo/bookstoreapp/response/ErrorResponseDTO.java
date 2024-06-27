package com.demo.bookstoreapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponseDTO {

  @Schema(description = "Status of the error response", example = "error")
  private String status;

  @Schema(description = "HTTP status code of the error response", example = "400")
  private int statusCode;

  @Schema(description = "List of error messages describing the issues", example = "[\"Title is required\", \"Author name must be between 1 and 30 characters\"]")
  private List<String> message;

}
