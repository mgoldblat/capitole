package com.capitole.exam.repository;

import com.capitole.exam.domain.Currency;
import com.capitole.exam.domain.Price;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

@Builder
public class PriceSpecification implements Specification<Price> {

  private static final String PROPERTY_START_DATE = "startDate";
  private static final String PROPERTY_END_DATE = "endDate";
  private static final String PROPERTY_PRODUCT_ID = "productId";
  private static final String PROPERTY_BRAND_ID = "brandId";

  private LocalDateTime dateTime;
  private long productId;
  private long brandId;
  private Currency currency;

  @Override
  public Predicate toPredicate(Root<Price> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    if (dateTime != null) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(PROPERTY_START_DATE), dateTime));
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(PROPERTY_END_DATE), dateTime));
    }

    if (productId != 0) {
      predicates.add(criteriaBuilder.equal(root.get(PROPERTY_PRODUCT_ID), productId));
    }

    if (productId != 0) {
      predicates.add(criteriaBuilder.equal(root.get(PROPERTY_BRAND_ID), brandId));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
