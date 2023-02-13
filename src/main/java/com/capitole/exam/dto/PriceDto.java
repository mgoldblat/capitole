package com.capitole.exam.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PriceDto {

  private LocalDateTime dateTime;
  private long productId;
  private int brandId;
}
