package com.demo.bookstoreapp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class BookRequestDTO {

  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
  private String title;

  @NotBlank(message = "Author is required")
  @Size(min = 1, max = 30, message = "Author name must be between 1 and 30 characters")
  private String author;

  @Size(min = 1, max = 1000, message = "Summary must be between 1 and 100 characters")
  private String summary;

  @NotNull(message = "Publish year is required")
  private int publishYear;

  @NotBlank(message = "ISBN is required")
  @Size(min = 1, max = 13, message = "ISBN must be between 1 and 13 characters")
  private String isbn;

}