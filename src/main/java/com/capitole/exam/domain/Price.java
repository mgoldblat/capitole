package com.capitole.exam.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Price {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long brandId;
  private String startDate;
  private String endDate;
  private int priceList;
  private long productId;
  private int priority;
  private BigDecimal price;
  private String curr;
}
