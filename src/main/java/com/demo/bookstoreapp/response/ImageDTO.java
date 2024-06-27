package com.demo.bookstoreapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ImageDTO {

  @Schema(description = "Unique identifier for the image")
  private String id;

  @Schema(description = "Public identifier for the image")
  private String publicId;

  @Schema(description = "URL of the image")
  private String url;

  @Schema(description = "Date when the image was created")
  private Date createdDate;

  @Schema(description = "Date when the image was last modified")
  private Date lastModifiedDate;

}
