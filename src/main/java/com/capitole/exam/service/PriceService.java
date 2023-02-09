package com.capitole.exam.service;

import com.capitole.exam.dto.PriceDto;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  public Map<String, Object> search(PriceDto dto) {
    return Map.of("price", "12345.5");
  }
}
