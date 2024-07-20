package com.demo.bookstoreapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookResponseDTO {

  @Schema(description = "Unique identifier for the book", example = "12345")
  private String id;

  @Schema(description = "Title of the book", example = "The Great Gatsby")
  private String title;

  @Schema(description = "Author of the book", example = "F. Scott Fitzgerald")
  private String author;

  @Schema(description = "Summary of the book", example = "A story of decadence and excess in the Jazz Age.")
  private String summary;

  @Schema(description = "Year of publication", example = "1925")
  private int publishYear;

  @Schema(description = "ISBN of the book", example = "9780743273565")
  private String isbn;

  @Schema(description = "Image details of the book")
  private ImageDTO image;

  @Schema(description = "Date when the book record was created", example = "2023-05-15T10:30:00Z")
  private Date createdDate;

  @Schema(description = "Date when the book record was last modified", example = "2023-06-20T14:45:00Z")
  private Date lastModifiedDate;

}
