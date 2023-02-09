package com.capitole.exam.controller;

import com.capitole.exam.dto.PriceDto;
import com.capitole.exam.service.PriceService;
import java.util.Map;
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

  private final PriceService priceService;

  @PostMapping("/search")
  public ResponseEntity<Map<String, Object>> search(@RequestBody PriceDto dto) {
    return new ResponseEntity<>(priceService.search(dto), HttpStatus.OK);
  }
}
