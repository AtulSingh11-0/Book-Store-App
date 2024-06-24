package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ErrorResponseDTO {

  private String status;
  private int statusCode;
  private List<String> message;

}
