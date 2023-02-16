package com.capitole.exam.dto;

import com.capitole.exam.domain.Currency;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {

  private LocalDateTime dateTime;
  private long productId;
  private long brandId;
  @Builder.Default
  private String curr = Currency.EUR.name();
}
