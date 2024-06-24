package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookResponseDTO {

  private String id;
  private String title;
  private String author;
  private String summary;
  private int publishYear;
  private String isbn;
  private Date createdDate;
  private Date lastModifiedDate;

}
