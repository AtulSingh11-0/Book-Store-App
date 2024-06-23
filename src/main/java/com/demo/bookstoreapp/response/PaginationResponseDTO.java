package com.demo.bookstoreapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponseDTO {

  private int currentPage;
  private int currentItem;
  private int totalPages;
  private long totalItems;
  private boolean hasNext;
  private boolean hasPrevious;
  private String sort;
  private String sortBy;
  private String order;

}