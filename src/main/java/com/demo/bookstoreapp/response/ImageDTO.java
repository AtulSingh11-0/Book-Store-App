package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDTO {

  private String id;
  private String name;
  private String type;
  private byte[] data;

}
