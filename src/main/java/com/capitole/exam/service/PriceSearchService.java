package com.capitole.exam.service;

import com.capitole.exam.domain.Price;
import com.capitole.exam.dto.PriceDto;
import com.capitole.exam.exception.PriceNotFoundException;
import com.capitole.exam.exception.PriceSearchValidationException;
import com.capitole.exam.repository.PriceRepository;
import com.capitole.exam.repository.PriceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceSearchService {

  private static final int QUERY_PAGE = 0;
  private static final int QUERY_SIZE = 1;
  private static final Sort QUERY_SORT = Sort.by(
      new Sort.Order(Sort.Direction.DESC, "priority"),
      new Sort.Order(Sort.Direction.DESC, "startDate"));

  private final PriceRepository priceRepository;

  public Price search(PriceDto dto) {
    validateDto(dto);

    PriceSpecification priceSpecification = PriceSpecification.builder()
        .dateTime(dto.getDateTime())
        .productId(dto.getProductId())
        .brandId(dto.getBrandId())
        .curr(dto.getCurr())
        .build();

    Page<Price> prices = priceRepository.findAll(
        priceSpecification,
        PageRequest.of(QUERY_PAGE, QUERY_SIZE, QUERY_SORT));

    return prices
        .get()
        .findFirst()
        .orElseThrow(() -> new PriceNotFoundException("price_not_found", "Missing price"));
  }

  private void validateDto(PriceDto dto) {
    if (dto.getDateTime() == null) {
      throw new PriceSearchValidationException("date_time_required", "date_time required");
    }

    if (dto.getProductId() == 0) {
      throw new PriceSearchValidationException("product_id_required", "product_id required");
    }

    if (dto.getBrandId() == 0) {
      throw new PriceSearchValidationException("brand_id_required", "brand_id required");
    }
  }
}
