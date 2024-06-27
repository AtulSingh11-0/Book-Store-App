package com.demo.bookstoreapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "images")
public class Image {

  @Id
  @Schema(description = "Unique identifier for the image")
  private String id;

  @Schema(description = "Public identifier for the image", example = "abc123")
  private String publicId;

  @Schema(description = "URL of the image", example = "https://example.com/image.jpg")
  private String url;

  @CreatedDate
  @Schema(description = "Date when the image record was created", example = "2023-05-15T10:30:00Z")
  private Date createdDate;

  @LastModifiedDate
  @Schema(description = "Date when the image record was last modified", example = "2023-06-20T14:45:00Z")
  private Date lastModifiedDate;

}
