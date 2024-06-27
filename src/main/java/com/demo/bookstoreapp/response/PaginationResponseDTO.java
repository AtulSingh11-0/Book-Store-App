package com.demo.bookstoreapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponseDTO {

  @Schema(description = "Current page number in pagination")
  private int currentPage;

  @Schema(description = "Number of items in the current page")
  private int currentItem;

  @Schema(description = "Total number of pages available")
  private int totalPages;

  @Schema(description = "Total number of items across all pages")
  private long totalItems;

  @Schema(description = "Flag indicating if there is a next page")
  private boolean hasNext;

  @Schema(description = "Flag indicating if there is a previous page")
  private boolean hasPrevious;

  @Schema(description = "Sorting criteria applied")
  private String sort;

  @Schema(description = "Field used for sorting")
  private String sortBy;

  @Schema(description = "Order of sorting (asc/desc)")
  private String order;

}
