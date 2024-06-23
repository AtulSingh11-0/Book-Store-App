package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {

  private String id;
  private String title;
  private String author;
  private String summary;
  private int publishYear;
  private String isbn;

}
