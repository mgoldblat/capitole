package com.capitole.exam.controller;

import com.capitole.exam.domain.Price;
import com.capitole.exam.dto.PriceDto;
import com.capitole.exam.dto.SearchResultDto;
import com.capitole.exam.service.PriceSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

  private final PriceSearchService priceSearchService;

  @PostMapping("/search")
  public ResponseEntity<SearchResultDto<Price>> search(@RequestBody PriceDto dto) {
    return new ResponseEntity<>(priceSearchService.search(dto), HttpStatus.OK);
  }
}
