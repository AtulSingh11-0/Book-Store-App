package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ImageDTO {

  private String id;
  private String publicId;
  private String url;
  private Date createdDate;
  private Date lastModifiedDate;

}
