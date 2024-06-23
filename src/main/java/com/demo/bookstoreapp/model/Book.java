package com.demo.bookstoreapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

  @Id
  private String id;

  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
  private String title;

  @NotBlank(message = "Author is required")
  @Size(min = 1, max = 30, message = "Author name must be between 1 and 30 characters")
  private String author;

  @Size(min = 1, max = 1000, message = "Summary must be between 1 and 100 characters")
  private String summary;

  @NotBlank(message = "Publish Year is required")
  private int publishYear;

  @NotBlank(message = "ISBN is required")
  @Size(min = 1, max = 13, message = "ISBN must be between 1 and 13 characters")
  private String isbn;

}
