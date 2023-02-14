package com.capitole.exam.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long brandId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private int priceList;
  private long productId;
  private int priority;
  private BigDecimal price;
  @Enumerated(EnumType.STRING)
  private Currency curr;
}
