package com.capitole.exam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.capitole.exam.domain.Currency;
import com.capitole.exam.domain.Price;
import com.capitole.exam.dto.PriceDto;
import com.capitole.exam.dto.SearchResultDto;
import com.capitole.exam.exception.PriceNotFoundException;
import com.capitole.exam.exception.PriceSearchValidationException;
import com.capitole.exam.repository.PriceRepository;
import com.capitole.exam.repository.PriceSpecification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PriceSearchServiceTest {

  private final PriceSearchService priceSearchService;
  private final PriceRepository priceRepository;

  PriceSearchServiceTest() {
    priceRepository = mock(PriceRepository.class);
    priceSearchService = new PriceSearchService(priceRepository);
  }

  private static Stream<Arguments> searchPriceFailsArgs() {
    return Stream.of(
        Arguments.arguments(
            PriceDto.builder()
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-14T10:00:00"))
                .build(),
            "brand_id_required"),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .dateTime(LocalDateTime.parse("2020-06-14T16:00:00"))
                .build(),
            "product_id_required"),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .build(),
            "date_time_required"));
  }

  @Test
  public void searchPriceShouldReturnPrice() {
    long priceId = 1;
    ArgumentCaptor<PriceSpecification> priceSpecificationCaptor = ArgumentCaptor.forClass(PriceSpecification.class);
    ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);
    Mockito.when(priceRepository.findAll(
            priceSpecificationCaptor.capture(),
            pageRequestCaptor.capture()))
        .thenReturn(new PageImpl<>(List.of(Price.builder().id(priceId).build())));

    PriceDto dto = PriceDto.builder()
        .brandId(2)
        .productId(3)
        .dateTime(LocalDateTime.now())
        .curr(Currency.EUR.name())
        .build();
    SearchResultDto<Price> result = priceSearchService.search(dto);

    assertEquals(1, result.getTotal());
    assertEquals(result.getData().get(0).getId(), priceId);

    verify(priceRepository, times(1)).findAll(
        priceSpecificationCaptor.capture(), pageRequestCaptor.capture());
  }

  @ParameterizedTest
  @MethodSource("searchPriceFailsArgs")
  public void searchPriceShouldThrowValidationException(PriceDto dto, String error) {
    PriceSearchValidationException e = assertThrows(PriceSearchValidationException.class,
        () -> priceSearchService.search(dto));
    assertEquals(e.getCode(), error);

    verify(priceRepository, times(0)).findAll(
        any(PriceSpecification.class), any(PageRequest.class));
  }

  @Test
  public void searchPriceShouldThrowNotFoundException() {
    long brandId = 2;
    long productId = 3;
    LocalDateTime date = LocalDateTime.now();

    ArgumentCaptor<PriceSpecification> priceSpecificationCaptor = ArgumentCaptor.forClass(PriceSpecification.class);
    ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);

    Mockito.when(priceRepository.findAll(
            priceSpecificationCaptor.capture(),
            pageRequestCaptor.capture()))
        .thenReturn(Page.empty());

    PriceDto dto = PriceDto.builder()
        .brandId(brandId)
        .productId(productId)
        .dateTime(date)
        .build();

    PriceNotFoundException e = assertThrows(PriceNotFoundException.class,
        () -> priceSearchService.search(dto));
    assertEquals(e.getCode(), "price_not_found");

    verify(priceRepository, times(1)).findAll(
        priceSpecificationCaptor.capture(), pageRequestCaptor.capture());
  }
}
