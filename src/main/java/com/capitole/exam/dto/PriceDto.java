package com.capitole.exam.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {

  private LocalDateTime dateTime;
  private long productId;
  private long brandId;
}
