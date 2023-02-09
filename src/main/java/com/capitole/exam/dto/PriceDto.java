package com.capitole.exam.dto;

import lombok.Data;

@Data
public class PriceDto {

  private String dateTime;
  private long productId;
  private int brandId;
}
