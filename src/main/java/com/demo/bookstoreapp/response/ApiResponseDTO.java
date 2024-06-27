package com.demo.bookstoreapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {

  @Schema(description = "Status of the API response", example = "success")
  private String status;

  @Schema(description = "HTTP status code of the response", example = "200")
  private int statusCode;

  @Schema(description = "Message describing the result of the API operation", example = "Book retrieved successfully")
  private String message;

  @Schema(description = "Data returned in the API response")
  private T data;

  @Schema(description = "Pagination details if applicable")
  private PaginationResponseDTO pagination;

}
