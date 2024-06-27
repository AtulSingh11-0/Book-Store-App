package com.demo.bookstoreapp.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestDTO {

  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
  @Schema(description = "Title of the book", example = "The Great Gatsby", required = true, maxLength = 50)
  private String title;

  @NotBlank(message = "Author is required")
  @Size(min = 1, max = 30, message = "Author name must be between 1 and 30 characters")
  @Schema(description = "Author of the book", example = "F. Scott Fitzgerald", required = true, maxLength = 30)
  private String author;

  @Size(min = 50, max = 1000, message = "Summary must be between 50 and 1000 characters")
  @Schema(description = "Summary of the book", example = "A story of decadence and excess in the Jazz Age.")
  private String summary;

  @NotNull(message = "Publish year is required")
  @Schema(description = "Year of publication", example = "1925", required = true)
  private int publishYear;

  @NotBlank(message = "ISBN is required")
  @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
  @Schema(description = "ISBN of the book", example = "9780743273565", required = true, minLength = 10, maxLength = 13)
  private String isbn;

}
