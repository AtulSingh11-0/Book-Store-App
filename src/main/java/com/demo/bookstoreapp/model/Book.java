package com.demo.bookstoreapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

  @Id
  @Schema(description = "Unique identifier for the book")
  private String id;

  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
  @Schema(description = "Title of the book", example = "The Great Gatsby", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
  private String title;

  @NotBlank(message = "Author is required")
  @Size(min = 1, max = 30, message = "Author name must be between 1 and 30 characters")
  @Schema(description = "Author of the book", example = "F. Scott Fitzgerald", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 30)
  private String author;

  @Size(min = 50, max = 1000, message = "Summary must be between 50 and 1000 characters")
  @Schema(description = "Summary of the book", example = "A story of decadence and excess in the Jazz Age.")
  private String summary;

  @NotNull(message = "Publish year is required")
  @Schema(description = "Year of publication", example = "1925", requiredMode = Schema.RequiredMode.REQUIRED)
  private int publishYear;

  @NotBlank(message = "ISBN is required")
  @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
  @Schema(description = "ISBN of the book", example = "9780743273565", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 10, maxLength = 13)
  private String isbn;

  @DBRef
  @Schema(description = "Reference to the book's image")
  private Image image;

  @CreatedDate
  @Schema(description = "Date when the book record was created", example = "2023-05-15T10:30:00Z")
  private Date createdDate;

  @LastModifiedDate
  @Schema(description = "Date when the book record was last modified", example = "2023-06-20T14:45:00Z")
  private Date lastModifiedDate;

}
